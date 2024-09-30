package com.booksms.shipment.interfaceLayer.service;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.interfaceLayer.dto.request.UpdateShipmentDetailDTO;

public interface IUpdateShipmentDetailService {
    ShipmentDetails updateById(Integer id, UpdateShipmentDetailDTO request);
}
