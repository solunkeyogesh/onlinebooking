package com.onlineorder.oms.payment.repository;

import com.onlineorder.oms.payment.domain.entity.PaymentTxn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentTxn, String> {
}
