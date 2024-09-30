package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.application.model.ShipmentServiceModel;
import com.booksms.shipment.application.usecase.FindShipmentServiceUseCase;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.interfaceLayer.service.IFindShipmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindShipmentService implements IFindShipmentService {
    private final FindShipmentServiceUseCase findShipmentServiceUseCase;
    private final ModelMapper modelMapper;


    @Override
    public ShipmentServiceModel findShipmentServiceById(Integer id) {
        ShipmentService shipmentService = findShipmentServiceUseCase.execute(
                List.of(Criteria.builder()
                                .key("id")
                                .operator(":")
                                .value(id)
                        .build())
        ).get(0);
        return modelMapper.map(shipmentService, ShipmentServiceModel.class);
    }

    @Override
    public List<ShipmentServiceModel> findAll() {
        List<ShipmentService> shipmentServices = findShipmentServiceUseCase.execute(null);
        return shipmentServices.stream()
                .map(shipmentService -> modelMapper.map(shipmentService, ShipmentServiceModel.class))
                .toList();
    }
}
