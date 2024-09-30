package com.booksms.shipment.application.usecase;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.application.model.ShipmentServiceModel;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.core.domain.repository.IShipmentServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FindShipmentServiceUseCase {
    private final IShipmentServiceRepository shipmentServiceRepository;
    private final MapperUseCase mapperUseCase;

    public List<ShipmentService> execute(List<Criteria> criteriaList) {
        List<ShipmentService> shipmentServices;

        if(criteriaList == null || criteriaList.isEmpty()) {
            return shipmentServiceRepository.findAll();
        }

        shipmentServices = shipmentServiceRepository.findByCriteria(criteriaList);
        if(shipmentServices.isEmpty()) {
            return null;
        }

        return shipmentServices;
    }


}
