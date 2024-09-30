package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.interfaceLayer.dto.request.UpdateShipmentDetailDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentDetailsResponse;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;

import java.util.List;

public interface IShipmentService {
    ShipmentResponse getShipment(String from, String to, Integer id);

    List<ShipmentResponse> getAllShipmentServices();

    ShipmentModel createShipment(ShipmentModel shipmentModel);

    List<ShipmentResponse> getShipments(String from, String to);

    List<ShipmentDetailsResponse> getAllShipmentDetails();

    ShipmentDetailsResponse getById(Integer id);

    ShipmentDetailsResponse updateById(Integer id, UpdateShipmentDetailDTO request);
}
