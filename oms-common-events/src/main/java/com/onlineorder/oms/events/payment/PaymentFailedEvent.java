package com.onlineorder.oms.events.payment;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {
    private String paymentId;
    private String orderId;
    private String reason;
    private Instant failedAt;
}