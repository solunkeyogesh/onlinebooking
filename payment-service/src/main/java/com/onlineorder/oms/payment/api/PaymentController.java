package com.onlineorder.oms.payment.api;

import com.onlineorder.oms.payment.api.request.AuthorizePaymentRequest;
import com.onlineorder.oms.payment.api.response.PaymentTxnView;
import com.onlineorder.oms.payment.domain.service.PaymentAuthorizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
	private final PaymentAuthorizer authorizer;

	@PostMapping("/authorize")
	public ResponseEntity<PaymentTxnView> authorize(@RequestBody AuthorizePaymentRequest req) {
		return ResponseEntity.ok(authorizer.authorizeManual(req));
	}
}
