package com.onlineorder.oms.order.api.response;

import com.onlineorder.oms.common.dto.CustomerDto;
import com.onlineorder.oms.common.dto.OrderItemDto;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Value
@Builder
public class OrderView {
    String orderId;
    CustomerDto customer;
    List<OrderItemDto> items;
    String status;
    BigDecimal totalAmount;
    String currency;
    Instant createdAt;
}
