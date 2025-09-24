package com.onlineorder.oms.notification.messaging.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeadLetterPublisher {
  private final KafkaTemplate<String, Object> kafka;
  public void publish(String topic, String key, Object payload) {
    kafka.send(topic, key, payload);
  }
}
