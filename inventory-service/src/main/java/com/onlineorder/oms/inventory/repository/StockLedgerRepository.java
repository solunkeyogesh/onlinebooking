package com.onlineorder.oms.inventory.repository;

import com.onlineorder.oms.inventory.domain.entity.StockLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLedgerRepository extends JpaRepository<StockLedger, Long> {}
