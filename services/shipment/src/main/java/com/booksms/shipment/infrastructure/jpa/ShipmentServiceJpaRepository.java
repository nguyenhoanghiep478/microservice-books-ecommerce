package com.booksms.shipment.infrastructure.jpa;

import com.booksms.shipment.core.domain.entity.ShipmentService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentServiceJpaRepository extends JpaRepository<ShipmentService,Integer> {
}
