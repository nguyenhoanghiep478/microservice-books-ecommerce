package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;

import java.util.List;

public interface IFindShipmentDetailService {
    List<ShipmentDetails> getAll();

    ShipmentDetails getById(Integer id);
}
