package com.onlineorder.oms.payment.api.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AuthorizePaymentRequest {
	private String orderId;
	private BigDecimal amount;
	private String currency; // e.g., "INR"
	private String provider; // "FAKE" or "STRIPE"
}
