package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.application.usecase.FindShipmentDetailsUseCase;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.interfaceLayer.service.IFindShipmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentDetailService implements IFindShipmentDetailService {
    private final FindShipmentDetailsUseCase findShipmentDetailUseCase;
    @Override
    public List<ShipmentDetails> getAll() {
        return findShipmentDetailUseCase.execute(null);
    }

    @Override
    public ShipmentDetails getById(Integer id) {
        return findShipmentDetailUseCase.execute(List.of(
                Criteria.builder()
                        .key("id")
                        .operator(":")
                        .value(id)
                        .build()
        )).get(0);
    }
}
