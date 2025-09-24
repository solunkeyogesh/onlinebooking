package com.onlineorder.oms.events.order;


import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdatedEvent {
    private String orderId;
    private String status;   // e.g. CREATED, PAID, CANCELLED
    private Instant updatedAt;
}
