package com.onlineorder.oms.inventory.domain.entity;

import com.onlineorder.oms.inventory.domain.model.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name = "reservation")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 64, nullable = false)
  private String orderId;

  @Column(length = 64, nullable = false)
  private String skuCode;

  @Column(precision = 19, scale = 2, nullable = false)
  private BigDecimal quantity;

  @Enumerated(EnumType.STRING)
  private ReservationStatus status;

  private Instant createdAt;
  private Instant updatedAt;
}
