package com.booksms.shipment.infrastructure.repository;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.core.domain.repository.IShipmentServiceRepository;
import com.booksms.shipment.infrastructure.jpa.ShipmentServiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShipmentServiceRepository implements IShipmentServiceRepository {
    private final ShipmentServiceJpaRepository shipmentServiceJpaRepository;


    @Override
    public List<ShipmentService> findAll() {
        return List.of();
    }

    @Override
    public List<ShipmentService> findByCriteria(List<Criteria> criteriaList) {
        return List.of();
    }

    @Override
    public Optional<ShipmentService> findById(Integer shipmentServiceId) {
        return shipmentServiceJpaRepository.findById(shipmentServiceId);
    }
}
