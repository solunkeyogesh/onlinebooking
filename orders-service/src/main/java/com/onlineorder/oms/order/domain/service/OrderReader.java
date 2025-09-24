package com.onlineorder.oms.order.domain.service;

import com.onlineorder.oms.common.dto.CustomerDto;
import com.onlineorder.oms.common.dto.OrderItemDto;
import com.onlineorder.oms.order.api.response.OrderView;
import com.onlineorder.oms.order.domain.entity.OrderHdr;
import com.onlineorder.oms.order.domain.entity.OrderItem;
import com.onlineorder.oms.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderReader {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Optional<OrderView> find(String orderId) {
        return orderRepository.findById(orderId)
                .map(o -> toView(o, null));
    }

    @Transactional(readOnly = true)
    public List<OrderView> findAll() {
        return orderRepository.findAll()
                .stream().map(o -> toView(o, null)).toList();
    }

    public OrderView toView(OrderHdr o, CustomerDto customer) {
        List<OrderItemDto> items = o.getItems().stream()
                .map(i -> OrderItemDto.builder()
                        .skuCode(i.getSkuCode())
                        .name(i.getName())
                        .quantity(i.getQuantity())
                        .price(com.onlineorder.oms.common.dto.MoneyDto.builder()
                                .amount(i.getUnitPrice().getAmount())
                                .currency(i.getUnitPrice().getCurrency())
                                .build())
                        .build())
                .toList();

        return OrderView.builder()
                .orderId(o.getOrderId())
                .customer(customer) // may be null if not passed
                .items(items)
                .status(o.getStatus().name())
                .totalAmount(o.getTotal().getAmount())
                .currency(o.getTotal().getCurrency())
                .createdAt(o.getCreatedAt())
                .build();
    }
}
