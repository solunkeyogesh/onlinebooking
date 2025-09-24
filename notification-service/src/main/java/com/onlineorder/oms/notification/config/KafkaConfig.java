package com.onlineorder.oms.notification.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Bean
  public ProducerFactory<String, Object> producerFactory(org.springframework.boot.autoconfigure.kafka.KafkaProperties props) {
    Map<String, Object> cfg = new HashMap<>(props.buildProducerProperties(null));
    cfg.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return new DefaultKafkaProducerFactory<>(cfg);
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
    return new KafkaTemplate<>(pf);
  }

  @Bean
  public DefaultKafkaProducerFactoryCustomizer idempotent() {
    return f -> f.updateConfigs(Map.of(
        ProducerConfig.ACKS_CONFIG, "all",
        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"
    ));
  }
}
