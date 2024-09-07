package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.application.usecase.CreateShipmentUseCase;
import com.booksms.shipment.core.domain.state.Status;
import com.booksms.shipment.interfaceLayer.service.ICreateShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateShipmentService implements ICreateShipmentService {
    private final CreateShipmentUseCase createShipmentUseCase;


    @Override
    public ShipmentModel createShipment(ShipmentModel shipmentModel) {
        return createShipmentUseCase.execute(shipmentModel);
    }
}
