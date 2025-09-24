package com.onlineorder.oms.events.order;

import com.onlineorder.oms.common.dto.CustomerDto;
import com.onlineorder.oms.common.dto.OrderItemDto;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private CustomerDto customer;
    private List<OrderItemDto> items;
    private Instant createdAt;
}