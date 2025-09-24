package com.onlineorder.oms.order.api.request;

import com.onlineorder.oms.common.dto.CustomerDto;
import com.onlineorder.oms.common.dto.OrderItemDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotNull
    private CustomerDto customer;
    @NotNull
    private List<OrderItemDto> items;
    private String currency = "INR";
}
