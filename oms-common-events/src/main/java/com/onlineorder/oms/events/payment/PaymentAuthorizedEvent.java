package com.onlineorder.oms.events.payment;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAuthorizedEvent {
    private String paymentId;
    private String orderId;
    private String status;  // AUTHORIZED
    private Instant authorizedAt;
}