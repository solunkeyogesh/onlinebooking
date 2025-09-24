package com.onlineorder.oms.events.payment;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCapturedEvent {
    private String paymentId;
    private String orderId;
    private String status;  // CAPTURED
    private Instant capturedAt;
}