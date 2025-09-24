package com.onlineorder.oms.payment.domain.entity;

import com.onlineorder.oms.payment.domain.model.PaymentStatus;
import com.onlineorder.oms.payment.domain.model.Provider;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment_txn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTxn {

	@Id
	@Column(length = 64)
	private String paymentId;

	@Column(length = 64, nullable = false)
	private String orderId;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	private BigDecimal amount;

	@Column(length = 8)
	private String currency;

	@Enumerated(EnumType.STRING)
	private Provider provider;

	private Instant createdAt;
	private Instant updatedAt;
}
