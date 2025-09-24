package com.onlineorder.oms.inventory.api;

import com.onlineorder.oms.inventory.domain.entity.Sku;
import com.onlineorder.oms.inventory.domain.service.StockAdjuster;
import com.onlineorder.oms.inventory.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

  private final SkuRepository skuRepo;
  private final StockAdjuster stockAdjuster;

  @GetMapping("/skus")
  public List<Sku> listSkus() {
    return skuRepo.findAll();
  }

  @PostMapping("/skus/{sku}/add/{qty}")
  public ResponseEntity<Void> add(@PathVariable String sku, @PathVariable int qty) {
    stockAdjuster.adjust(sku, BigDecimal.valueOf(qty));
    return ResponseEntity.ok().build();
  }

  @PostMapping("/skus/{sku}/remove/{qty}")
  public ResponseEntity<Void> remove(@PathVariable String sku, @PathVariable int qty) {
    stockAdjuster.adjust(sku, BigDecimal.valueOf(-qty));
    return ResponseEntity.ok().build();
  }
}
