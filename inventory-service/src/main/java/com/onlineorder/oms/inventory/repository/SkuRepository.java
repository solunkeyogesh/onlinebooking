package com.onlineorder.oms.inventory.repository;

import com.onlineorder.oms.inventory.domain.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, String> {}
