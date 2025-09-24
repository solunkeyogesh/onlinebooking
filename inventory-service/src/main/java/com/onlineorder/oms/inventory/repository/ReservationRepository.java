package com.onlineorder.oms.inventory.repository;

import com.onlineorder.oms.inventory.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
