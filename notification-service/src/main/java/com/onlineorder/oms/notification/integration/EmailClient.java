package com.onlineorder.oms.notification.integration;

import com.onlineorder.oms.notification.domain.model.TemplateKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class EmailClient {
  public void send(String to, String subject, String body) {
    // Plug your SMTP / provider here; for now just log.
    log.info("EMAIL → to={} subj={} body={}", to, subject, body);
  }

  public String render(TemplateKey key, Map<String, Object> vars) {
    return switch (key) {
      case ORDER_CREATED     -> "Your order " + vars.get("orderId") + " was created.";
      case PAYMENT_AUTHORIZED-> "Payment authorized for " + vars.get("orderId") + ".";
      case PAYMENT_CAPTURED  -> "Payment captured for " + vars.get("orderId") + ".";
      case PAYMENT_FAILED    -> "Payment failed for " + vars.get("orderId") + ".";
    };
  }
}
