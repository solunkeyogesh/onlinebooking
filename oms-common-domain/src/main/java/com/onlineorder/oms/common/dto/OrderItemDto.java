package com.onlineorder.oms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private String skuCode;
    private String name;
    private int quantity;
    private MoneyDto price;  // per unit price
}