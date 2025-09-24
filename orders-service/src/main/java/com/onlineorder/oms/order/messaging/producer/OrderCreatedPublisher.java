package com.onlineorder.oms.order.messaging.producer;

import com.onlineorder.oms.common.constants.Topics;
import com.onlineorder.oms.events.order.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(OrderCreatedEvent event) {
        kafkaTemplate.send(Topics.ORDER_CREATED, event.getOrderId(), event);
    }
}
