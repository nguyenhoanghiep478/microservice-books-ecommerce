package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;

import java.util.List;

public interface IShipmentService {
    ShipmentResponse getShipment(String from, String to, ShipmentMethod shipmentMethod);

    List<ShipmentResponse> getAllShipmentServices();

    ShipmentModel createShipment(ShipmentModel shipmentModel);
}
