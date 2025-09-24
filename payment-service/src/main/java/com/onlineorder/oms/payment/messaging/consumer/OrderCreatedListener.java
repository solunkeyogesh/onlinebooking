package com.onlineorder.oms.payment.messaging.consumer;

import com.onlineorder.oms.events.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "order.created", groupId = "payment-service")
public class OrderCreatedListener {

	@KafkaHandler
	public void onEvent(OrderCreatedEvent evt) {
		log.info("Received OrderCreatedEvent orderId={} items={}", evt.getOrderId(), evt.getItems().size());
		// Optionally auto-authorize here by calling
		// PaymentAuthorizer.authorizeManual(...)
	}
}
