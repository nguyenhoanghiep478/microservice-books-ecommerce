package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.application.model.ShipmentServiceModel;

import java.util.List;

public interface IFindShipmentService {

    ShipmentServiceModel findShipmentServiceById(Integer id);

    List<ShipmentServiceModel> findAll();
}
