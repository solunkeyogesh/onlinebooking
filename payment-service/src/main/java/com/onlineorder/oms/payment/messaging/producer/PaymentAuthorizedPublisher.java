package com.onlineorder.oms.payment.messaging.producer;

import com.onlineorder.oms.common.constants.Topics;
import com.onlineorder.oms.events.payment.PaymentAuthorizedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentAuthorizedPublisher {
	private final KafkaTemplate<String, Object> kafka;

	public void publish(PaymentAuthorizedEvent evt) {
		kafka.send(Topics.PAYMENT_AUTHORIZED, evt.getOrderId(), evt);
	}
}
