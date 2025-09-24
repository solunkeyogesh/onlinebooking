package com.onlineorder.oms.inventory.messaging.producer;

import com.onlineorder.oms.events.inventory.InventoryFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryFailedPublisher {
  private final KafkaTemplate<String, Object> kafka;
  public void publish(InventoryFailedEvent evt) {
    kafka.send("inventory.failed", evt.getOrderId(), evt);
  }
}
