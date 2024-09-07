package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.usecase.FindShipmentServiceUseCase;
import com.booksms.shipment.interfaceLayer.service.IFindShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindShipmentService implements IFindShipmentService {
    private final FindShipmentServiceUseCase findShipmentServiceUseCase;



}
