package com.booksms.shipment.core.domain.repository;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;

import java.util.List;

public interface IShipmentDetailRepository {
    List<ShipmentDetails> findAll();

    List<ShipmentDetails> findByCriteria(List<Criteria> criteriaList);

    ShipmentDetails save(ShipmentDetails mergedShipmentDetail);
}
