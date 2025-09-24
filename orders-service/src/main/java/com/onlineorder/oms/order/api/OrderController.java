package com.onlineorder.oms.order.api;

import com.onlineorder.oms.order.api.request.CreateOrderRequest;
import com.onlineorder.oms.order.api.response.OrderView;
import com.onlineorder.oms.order.domain.service.OrderCreator;
import com.onlineorder.oms.order.domain.service.OrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderCreator orderCreator;
    private final OrderReader orderReader;

    @PostMapping
    public ResponseEntity<OrderView> create(@Validated @RequestBody CreateOrderRequest req) {
        OrderView created = orderCreator.create(req);
        return ResponseEntity.created(URI.create("/api/orders/" + created.getOrderId())).body(created);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderView> get(@PathVariable String orderId) {
        return ResponseEntity.of(orderReader.find(orderId));
    }

    @GetMapping
    public List<OrderView> list() {
        return orderReader.findAll();
    }
}
