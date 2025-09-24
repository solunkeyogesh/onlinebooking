package com.onlineorder.oms.notification.domain.service;

import com.onlineorder.oms.notification.domain.model.Channel;
import com.onlineorder.oms.notification.domain.model.TemplateKey;
import com.onlineorder.oms.notification.integration.EmailClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender implements NotificationSender {

  private final EmailClient emailClient;

  @Override
  public void send(Channel channel, String to, TemplateKey template, Map<String, Object> vars) {
    if (channel != Channel.EMAIL) return;
    String body = emailClient.render(template, vars);
    emailClient.send(to, "Notification: " + template.name(), body);
    log.info("Email sent to {} using template {}", to, template);
  }
}
