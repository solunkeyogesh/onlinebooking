package com.onlineorder.oms.payment.messaging.producer;

import com.onlineorder.oms.events.payment.PaymentFailedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentFailedPublisher {
	private final KafkaTemplate<String, Object> kafka;

	public void publish(PaymentFailedEvent evt) {
		kafka.send("payment.failed", evt.getOrderId(), evt);
	}
}
