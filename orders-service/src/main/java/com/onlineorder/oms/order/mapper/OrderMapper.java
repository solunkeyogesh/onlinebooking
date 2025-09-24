package com.onlineorder.oms.order.mapper;

import com.onlineorder.oms.common.dto.MoneyDto;
import com.onlineorder.oms.common.dto.OrderItemDto;
import com.onlineorder.oms.order.domain.entity.OrderItem;
import com.onlineorder.oms.order.domain.model.Money;

public class OrderMapper {

    public static OrderItem toEntity(OrderItemDto dto) {
        return OrderItem.builder()
                .skuCode(dto.getSkuCode())
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .unitPrice(Money.builder()
                        .amount(dto.getPrice().getAmount())
                        .currency(dto.getPrice().getCurrency())
                        .build())
                .build();
    }

    public static OrderItemDto toDto(OrderItem e) {
        return OrderItemDto.builder()
                .skuCode(e.getSkuCode())
                .name(e.getName())
                .quantity(e.getQuantity())
                .price(MoneyDto.builder()
                        .amount(e.getUnitPrice().getAmount())
                        .currency(e.getUnitPrice().getCurrency())
                        .build())
                .build();
    }
}
