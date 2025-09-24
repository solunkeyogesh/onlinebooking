package com.onlineorder.oms.payment.provider;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FakeGatewayClient implements StripeClient {
	@Override
	public boolean authorize(String orderId, BigDecimal amount, String currency) {
		// simple rule: approve amounts <= 5000
		return amount != null && amount.signum() >= 0 && amount.longValue() <= 5000;
	}
}
