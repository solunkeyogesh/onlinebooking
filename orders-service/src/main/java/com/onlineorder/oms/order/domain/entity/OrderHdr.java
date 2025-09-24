package com.onlineorder.oms.order.domain.entity;

import com.onlineorder.oms.order.domain.model.Money;
import com.onlineorder.oms.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_hdr")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHdr {

    @Id
    @Column(name = "order_id", length = 64)
    private String orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Money total;

    private Instant createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }
}
