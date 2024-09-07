package com.booksms.shipment.infrastructure.repository;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.repository.IShipmentRepository;
import com.booksms.shipment.infrastructure.jpa.ShipmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShipmentRepository implements IShipmentRepository {
    private final ShipmentJpaRepository shipmentJpaRepository;


    @Override
    public ShipmentDetails save(ShipmentDetails entity) {
        return shipmentJpaRepository.save(entity);
    }
}
