package com.onlineorder.oms.payment.api.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class PaymentTxnView {
	String paymentId;
	String orderId;
	String status;
	BigDecimal amount;
	String currency;
	String provider;
	Instant createdAt;
	Instant updatedAt;
}
