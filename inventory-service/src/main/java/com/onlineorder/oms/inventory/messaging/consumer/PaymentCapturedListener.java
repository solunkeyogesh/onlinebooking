package com.onlineorder.oms.inventory.messaging.consumer;

import com.onlineorder.oms.events.payment.PaymentCapturedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "payment.captured", groupId = "inventory-service")
public class PaymentCapturedListener {

  @KafkaHandler
  public void onMessage(PaymentCapturedEvent evt) {
    log.info("Payment captured orderId={} paymentId={}", evt.getOrderId(), evt.getPaymentId());
    // Optionally decrement onHand and reserved here if you model shipment on capture.
  }
}
