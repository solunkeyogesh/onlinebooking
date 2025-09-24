package com.onlineorder.oms.notification.messaging.consumer;

import com.onlineorder.oms.events.order.OrderCreatedEvent;
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
@KafkaListener(topics = "order.created", groupId = "notification-service")
public class OrderCreatedListener {

  private final EmailSender email;

  @KafkaHandler
  public void onEvent(OrderCreatedEvent evt) {
    log.info("Notify: order.created orderId={}", evt.getOrderId());
    String to = evt.getCustomer() != null ? evt.getCustomer().getEmail() : "test@example.com";
    email.send(Channel.EMAIL, to, TemplateKey.ORDER_CREATED,
        Map.of("orderId", evt.getOrderId(), "items", evt.getItems().size()));
  }
}
