package com.onlineorder.oms.order.domain.entity;

import com.onlineorder.oms.order.domain.model.Money;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skuCode;
    private String name;
    private int quantity;

    @Embedded
    private Money unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderHdr order;
}
