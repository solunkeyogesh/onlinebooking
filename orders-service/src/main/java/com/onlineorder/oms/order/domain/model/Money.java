package com.onlineorder.oms.order.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Money {
    private BigDecimal amount;
    private String currency;
}
