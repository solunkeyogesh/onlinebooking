package com.onlineorder.oms.events.inventory;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryFailedEvent {
    private String orderId;
    private String reason;
    private Instant failedAt;
}