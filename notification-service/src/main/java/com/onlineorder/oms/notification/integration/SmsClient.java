package com.onlineorder.oms.notification.integration;

import com.onlineorder.oms.notification.domain.model.TemplateKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SmsClient {
  public void send(String to, String text) {
    log.info("SMS → to={} text={}", to, text);
  }

  public String render(TemplateKey key, Map<String, Object> vars) {
    return switch (key) {
      case ORDER_CREATED      -> "Order " + vars.get("orderId") + " created";
      case PAYMENT_AUTHORIZED -> "Payment authorized: " + vars.get("orderId");
      case PAYMENT_CAPTURED   -> "Payment captured: " + vars.get("orderId");
      case PAYMENT_FAILED     -> "Payment failed: " + vars.get("orderId");
    };
  }
}
