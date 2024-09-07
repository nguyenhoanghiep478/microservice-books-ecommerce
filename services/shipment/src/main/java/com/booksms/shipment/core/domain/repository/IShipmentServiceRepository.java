package com.booksms.shipment.core.domain.repository;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.application.model.ShipmentServiceModel;
import com.booksms.shipment.core.domain.entity.ShipmentService;

import java.util.List;
import java.util.Optional;

public interface IShipmentServiceRepository {
    List<ShipmentService> findAll();

    List<ShipmentService> findByCriteria(List<Criteria> criteriaList);

    Optional<ShipmentService> findById(Integer shipmentServiceId);
}
