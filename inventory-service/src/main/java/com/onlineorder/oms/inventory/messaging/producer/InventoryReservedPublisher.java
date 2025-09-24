package com.onlineorder.oms.inventory.messaging.producer;

import com.onlineorder.oms.common.constants.Topics;
import com.onlineorder.oms.events.inventory.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryReservedPublisher {
  private final KafkaTemplate<String, Object> kafka;
  public void publish(InventoryReservedEvent evt) {
    kafka.send(Topics.INVENTORY_RESERVED, evt.getOrderId(), evt);
  }
}
