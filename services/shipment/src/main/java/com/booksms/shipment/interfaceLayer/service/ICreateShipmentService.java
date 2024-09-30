package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.application.model.ShipmentModel;

public interface ICreateShipmentService {
    ShipmentModel createShipment(ShipmentModel shipmentModel);
}
