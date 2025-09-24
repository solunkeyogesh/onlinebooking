package com.onlineorder.oms.order.messaging.consumer;

import com.onlineorder.oms.events.payment.PaymentAuthorizedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "payment.authorized", groupId = "order-service")
public class PaymentAuthorizedListener {

    @KafkaHandler
    public void onMessage(PaymentAuthorizedEvent evt) {
        log.info("Payment authorized for orderId={}, paymentId={}", evt.getOrderId(), evt.getPaymentId());
        // TODO: update order status to PAID (add repository + @Transactional handler if needed)
    }
}
