package com.onlineorder.oms.order.config;

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
        Map<String, Object> config = new HashMap<>(props.buildProducerProperties(null));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Value serializer is auto-configured to JsonSerializer by Spring Boot if spring-kafka is on classpath
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
        return new KafkaTemplate<>(pf);
    }

    // if you want to tweak defaults:
    @Bean
    public DefaultKafkaProducerFactoryCustomizer tuneProducer() {
        return factory -> {
            factory.updateConfigs(Map.of(
                    ProducerConfig.ACKS_CONFIG, "all",
                    ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"
            ));
        };
    }
}
