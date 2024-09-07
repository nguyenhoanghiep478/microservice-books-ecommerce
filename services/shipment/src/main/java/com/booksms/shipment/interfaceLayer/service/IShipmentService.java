package com.booksms.shipment.infrastructure.serviceGateway;

import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;

public interface IShipmentService {
    ShipmentResponse getShipment(String from, String to, ShipmentMethod shipmentMethod);
}
