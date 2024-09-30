package com.booksms.shipment.infrastructure.jpa;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentDetailJpaRepository extends JpaRepository<ShipmentDetails,Integer> {
}
