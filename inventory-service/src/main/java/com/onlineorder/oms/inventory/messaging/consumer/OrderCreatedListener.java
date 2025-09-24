package com.onlineorder.oms.inventory.messaging.consumer;

import com.onlineorder.oms.common.dto.OrderItemDto;
import com.onlineorder.oms.events.order.OrderCreatedEvent;
import com.onlineorder.oms.inventory.domain.service.InventoryReserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "order.created", groupId = "inventory-service")
public class OrderCreatedListener {

  private final InventoryReserver reserver;

  @KafkaHandler
  public void onMessage(OrderCreatedEvent evt) {
    log.info("OrderCreated received orderId={} items={}", evt.getOrderId(), evt.getItems().size());
    for (OrderItemDto item : evt.getItems()) {
      reserver.reserve(evt.getOrderId(), item.getSkuCode(), BigDecimal.valueOf(item.getQuantity()));
    }
  }
}
