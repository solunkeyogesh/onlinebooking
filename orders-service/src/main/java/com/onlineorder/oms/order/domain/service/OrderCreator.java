package com.onlineorder.oms.order.domain.service;

import com.onlineorder.oms.common.dto.OrderItemDto;
import com.onlineorder.oms.common.dto.CustomerDto;
import com.onlineorder.oms.events.order.OrderCreatedEvent;
import com.onlineorder.oms.order.api.request.CreateOrderRequest;
import com.onlineorder.oms.order.api.response.OrderView;
import com.onlineorder.oms.order.domain.entity.OrderHdr;
import com.onlineorder.oms.order.domain.entity.OrderItem;
import com.onlineorder.oms.order.domain.model.Money;
import com.onlineorder.oms.order.domain.model.OrderStatus;
import com.onlineorder.oms.order.messaging.producer.OrderCreatedPublisher;
import com.onlineorder.oms.order.repository.OrderRepository;
import com.onlineorder.oms.order.util.ClockProvider;
import com.onlineorder.oms.order.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderCreator {

    private final OrderRepository orderRepository;
    private final OrderReader orderReader;
    private final IdGenerator idGen;
    private final ClockProvider clock;
    private final OrderCreatedPublisher publisher;

    @Transactional
    public OrderView create(CreateOrderRequest req) {
        String orderId = idGen.generateId();
        Instant now = clock.now();

        // Compute total (sum of price.amount * qty)
        BigDecimal total = req.getItems().stream()
                .map(i -> i.getPrice().getAmount().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderHdr order = OrderHdr.builder()
                .orderId(orderId)
                .status(OrderStatus.CREATED)
                .createdAt(now)
                .total(Money.builder().amount(total).currency(req.getCurrency()).build())
                .build();

        for (OrderItemDto dto : req.getItems()) {
            OrderItem item = OrderItem.builder()
                    .skuCode(dto.getSkuCode())
                    .name(dto.getName())
                    .quantity(dto.getQuantity())
                    .unitPrice(Money.builder()
                            .amount(dto.getPrice().getAmount())
                            .currency(dto.getPrice().getCurrency())
                            .build())
                    .build();
            order.addItem(item);
        }

        orderRepository.save(order);

        // Publish event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(orderId)
                .customer(req.getCustomer())
                .items(req.getItems())
                .createdAt(now)
                .build();
        publisher.publish(event);

        // Return view
        return orderReader.toView(order, req.getCustomer());
    }
}
