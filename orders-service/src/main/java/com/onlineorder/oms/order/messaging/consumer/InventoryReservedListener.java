package com.onlineorder.oms.order.messaging.consumer;

import com.onlineorder.oms.events.inventory.InventoryReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "inventory.reserved", groupId = "order-service")
public class InventoryReservedListener {

    @KafkaHandler
    public void onMessage(InventoryReservedEvent evt) {
        log.info("Inventory reserved for orderId={}, success={}", evt.getOrderId(), evt.isSuccess());
        // TODO: transition to RESERVED, or handle failure
    }
}
