package com.bookms.order.infrastructure.serviceGateway;

import com.bookms.order.interfaceLayer.DTO.respone.ShipmentDetailsResponse;

public interface IShipmentServiceGateway {
    ShipmentDetailsResponse findShipmentById(Integer shipmentId);
}
