package com.onlineorder.oms.order.repository;

import com.onlineorder.oms.order.domain.entity.OrderHdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderHdr, String> {
}
