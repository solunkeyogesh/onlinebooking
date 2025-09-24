package com.onlineorder.oms.notification.domain.service;

import com.onlineorder.oms.notification.domain.model.Channel;
import com.onlineorder.oms.notification.domain.model.TemplateKey;
import com.onlineorder.oms.notification.integration.SmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsSender implements NotificationSender {

  private final SmsClient smsClient;

  @Override
  public void send(Channel channel, String to, TemplateKey template, Map<String, Object> vars) {
    if (channel != Channel.SMS) return;
    String text = smsClient.render(template, vars);
    smsClient.send(to, text);
    log.info("SMS sent to {} using template {}", to, template);
  }
}
