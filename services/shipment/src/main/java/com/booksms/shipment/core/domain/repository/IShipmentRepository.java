package com.booksms.shipment.core.domain.repository;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;

public interface IShipmentRepository {
    ShipmentDetails save(ShipmentDetails entity);
}
