package com.onlineorder.oms.inventory.domain.service;

import com.onlineorder.oms.events.inventory.InventoryFailedEvent;
import com.onlineorder.oms.events.inventory.InventoryReservedEvent;
import com.onlineorder.oms.inventory.domain.entity.Reservation;
import com.onlineorder.oms.inventory.domain.entity.Sku;
import com.onlineorder.oms.inventory.domain.model.ReservationStatus;
import com.onlineorder.oms.inventory.messaging.producer.InventoryFailedPublisher;
import com.onlineorder.oms.inventory.messaging.producer.InventoryReservedPublisher;
import com.onlineorder.oms.inventory.repository.ReservationRepository;
import com.onlineorder.oms.inventory.repository.SkuRepository;
import com.onlineorder.oms.inventory.util.ClockProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class InventoryReserver {

  private final SkuRepository skuRepo;
  private final ReservationRepository reservationRepo;
  private final InventoryReservedPublisher reservedPublisher;
  private final InventoryFailedPublisher failedPublisher;
  private final ClockProvider clock;

  @Transactional
  public void reserve(String orderId, String skuCode, BigDecimal qty) {
    Instant now = clock.now();
    Sku sku = skuRepo.findById(skuCode).orElse(null);

    if (sku == null || sku.getOnHand().subtract(sku.getReserved()).compareTo(qty) < 0) {
      reservationRepo.save(Reservation.builder()
          .orderId(orderId).skuCode(skuCode).quantity(qty)
          .status(ReservationStatus.FAILED).createdAt(now).updatedAt(now)
          .build());
      failedPublisher.publish(InventoryFailedEvent.builder()
          .orderId(orderId).reason("INSUFFICIENT_STOCK").failedAt(now).build());
      return;
    }

    sku.setReserved(sku.getReserved().add(qty));
    skuRepo.save(sku);

    reservationRepo.save(Reservation.builder()
        .orderId(orderId).skuCode(skuCode).quantity(qty)
        .status(ReservationStatus.RESERVED).createdAt(now).updatedAt(now)
        .build());

    reservedPublisher.publish(InventoryReservedEvent.builder()
        .orderId(orderId).success(true).reservedAt(now).build());
  }
}
