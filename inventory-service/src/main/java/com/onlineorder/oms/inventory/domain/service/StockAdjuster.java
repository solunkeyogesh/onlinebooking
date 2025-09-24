package com.onlineorder.oms.inventory.domain.service;

import com.onlineorder.oms.inventory.domain.entity.Sku;
import com.onlineorder.oms.inventory.domain.entity.StockLedger;
import com.onlineorder.oms.inventory.repository.SkuRepository;
import com.onlineorder.oms.inventory.repository.StockLedgerRepository;
import com.onlineorder.oms.inventory.util.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockAdjuster {

  private final SkuRepository skuRepo;
  private final StockLedgerRepository ledgerRepo;
  private final ClockProvider clock;

  @Transactional
  public void adjust(String skuCode, BigDecimal delta) {
    Sku sku = skuRepo.findById(skuCode)
        .orElseGet(() -> Sku.builder().skuCode(skuCode).name(skuCode).onHand(BigDecimal.ZERO).reserved(BigDecimal.ZERO).build());

    sku.setOnHand(sku.getOnHand().add(delta));
    skuRepo.save(sku);

    ledgerRepo.save(StockLedger.builder()
        .skuCode(skuCode)
        .delta(delta)
        .createdAt(clock.now())
        .build());
  }
}
