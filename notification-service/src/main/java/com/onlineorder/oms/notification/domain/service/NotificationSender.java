package com.onlineorder.oms.notification.domain.service;

import com.onlineorder.oms.notification.domain.model.Channel;
import com.onlineorder.oms.notification.domain.model.TemplateKey;

import java.util.Map;

public interface NotificationSender {
  void send(Channel channel, String to, TemplateKey template, Map<String, Object> vars);
}
