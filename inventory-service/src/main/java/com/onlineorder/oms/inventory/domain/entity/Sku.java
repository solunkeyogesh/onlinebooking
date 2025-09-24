package com.onlineorder.oms.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity @Table(name = "sku")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sku {
  @Id
  @Column(name = "sku_code", length = 64)
  private String skuCode;

  private String name;

  @Column(precision = 19, scale = 2)
  private BigDecimal onHand;      // current stock

  @Column(precision = 19, scale = 2)
  private BigDecimal reserved;    // reserved but not shipped
}
