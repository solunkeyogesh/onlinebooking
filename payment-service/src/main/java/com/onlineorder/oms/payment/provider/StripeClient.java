package com.onlineorder.oms.payment.provider;

import java.math.BigDecimal;

public interface StripeClient {
	boolean authorize(String orderId, BigDecimal amount, String currency);
}
