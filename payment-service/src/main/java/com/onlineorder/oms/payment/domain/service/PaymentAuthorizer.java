package com.onlineorder.oms.payment.domain.service;

import com.onlineorder.oms.events.payment.PaymentAuthorizedEvent;
import com.onlineorder.oms.events.payment.PaymentFailedEvent;
import com.onlineorder.oms.payment.api.request.AuthorizePaymentRequest;
import com.onlineorder.oms.payment.api.response.PaymentTxnView;
import com.onlineorder.oms.payment.domain.entity.PaymentTxn;
import com.onlineorder.oms.payment.domain.model.PaymentStatus;
import com.onlineorder.oms.payment.domain.model.Provider;
import com.onlineorder.oms.payment.messaging.producer.PaymentAuthorizedPublisher;
import com.onlineorder.oms.payment.messaging.producer.PaymentFailedPublisher;
import com.onlineorder.oms.payment.provider.FakeGatewayClient;
import com.onlineorder.oms.payment.provider.StripeClient;
import com.onlineorder.oms.payment.repository.PaymentRepository;
import com.onlineorder.oms.payment.util.ClockProvider;
import com.onlineorder.oms.payment.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentAuthorizer {

	private final PaymentRepository repo;
	private final IdGenerator ids;
	private final ClockProvider clock;
	private final FakeGatewayClient fake;
	private final StripeClient stripe;
	private final PaymentAuthorizedPublisher authorizedPub;
	private final PaymentFailedPublisher failedPub;

	@Transactional
	public PaymentTxnView authorizeManual(AuthorizePaymentRequest req) {
		String pid = ids.generateId();
		Instant now = clock.now();
		Provider provider = Provider.valueOf(req.getProvider().toUpperCase());

		boolean ok = switch (provider) {
		case FAKE -> fake.authorize(req.getOrderId(), req.getAmount(), req.getCurrency());
		case STRIPE -> stripe.authorize(req.getOrderId(), req.getAmount(), req.getCurrency());
		};

		PaymentTxn txn = PaymentTxn.builder().paymentId(pid).orderId(req.getOrderId())
				.status(ok ? PaymentStatus.AUTHORIZED : PaymentStatus.FAILED).amount(req.getAmount())
				.currency(req.getCurrency()).provider(provider).createdAt(now).updatedAt(now).build();
		repo.save(txn);

		if (ok) {
			authorizedPub.publish(PaymentAuthorizedEvent.builder().paymentId(pid).orderId(req.getOrderId())
					.status("AUTHORIZED").authorizedAt(now).build());
		} else {
			failedPub.publish(PaymentFailedEvent.builder().paymentId(pid).orderId(req.getOrderId())
					.reason("GATEWAY_DECLINED").failedAt(now).build());
		}

		return PaymentTxnView.builder().paymentId(pid).orderId(txn.getOrderId()).status(txn.getStatus().name())
				.amount(txn.getAmount()).currency(txn.getCurrency()).provider(txn.getProvider().name())
				.createdAt(txn.getCreatedAt()).updatedAt(txn.getUpdatedAt()).build();
	}
}
