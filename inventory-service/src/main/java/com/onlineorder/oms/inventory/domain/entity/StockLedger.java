package com.onlineorder.oms.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name = "stock_ledger")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockLedger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64, nullable = false)
  private String skuCode;

  @Column(precision = 19, scale = 2, nullable = false)
  private BigDecimal delta;           // +add / -remove / +reserve / -release

  @Column(length = 64)
  private String orderId;             // optional link to order

  private Instant createdAt;
}
