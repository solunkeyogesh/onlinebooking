package com.onlineorder.oms.notification.messaging.consumer;

import com.onlineorder.oms.events.payment.PaymentAuthorizedEvent;
import com.onlineorder.oms.events.payment.PaymentCapturedEvent;
import com.onlineorder.oms.events.payment.PaymentFailedEvent;
import com.onlineorder.oms.notification.domain.model.Channel;
import com.onlineorder.oms.notification.domain.model.TemplateKey;
import com.onlineorder.oms.notification.domain.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = {"payment.authorized", "payment.captured", "payment.failed"},
               groupId = "notification-service")
public class PaymentEventsListener {

  private final EmailSender email;

  @KafkaHandler
  public void onAuthorized(PaymentAuthorizedEvent evt) {
    notify(TemplateKey.PAYMENT_AUTHORIZED, evt.getOrderId(), evt.getPaymentId());
  }

  @KafkaHandler
  public void onCaptured(PaymentCapturedEvent evt) {
    notify(TemplateKey.PAYMENT_CAPTURED, evt.getOrderId(), evt.getPaymentId());
  }

  @KafkaHandler
  public void onFailed(PaymentFailedEvent evt) {
    notify(TemplateKey.PAYMENT_FAILED, evt.getOrderId(), evt.getPaymentId());
  }

  private void notify(TemplateKey key, String orderId, String paymentId) {
    email.send(Channel.EMAIL, "test@example.com", key,
        Map.of("orderId", orderId, "paymentId", paymentId));
    log.info("Notify: {} orderId={} paymentId={}", key, orderId, paymentId);
  }
}
