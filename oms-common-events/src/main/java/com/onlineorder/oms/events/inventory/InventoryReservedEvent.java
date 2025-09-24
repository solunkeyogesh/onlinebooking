package com.onlineorder.oms.events.inventory;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent {
    private String orderId;
    private boolean success;
    private Instant reservedAt;
}