package com.onlineorder.oms.notification.config;

import com.onlineorder.oms.notification.domain.model.TemplateKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class TemplateConfig {

  @Bean
  public Map<TemplateKey, String> templates() {
    // You could wire a real templating engine; this map is just a placeholder.
    return new EnumMap<>(TemplateKey.class);
  }
}
