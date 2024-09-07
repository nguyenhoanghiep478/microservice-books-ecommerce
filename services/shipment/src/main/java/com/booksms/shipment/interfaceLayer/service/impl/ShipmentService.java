package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.application.servicegateway.IMapService;
import com.booksms.shipment.interfaceLayer.service.ICreateShipmentService;
import com.booksms.shipment.interfaceLayer.service.IFindShipmentService;
import com.booksms.shipment.interfaceLayer.service.IShipmentService;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentService implements IShipmentService {
    private final IMapService mapService;
    private final IFindShipmentService findShipmentService;
    private final ICreateShipmentService createShipmentService;

    @Override
    public ShipmentResponse getShipment(String from, String to, ShipmentMethod shipmentMethod) {
        double distance = mapService.getDistance(from, to);

        double travelHours = distance/ shipmentMethod.getSpeed();

        int travelDays = (int) (travelHours / 24);

        return ShipmentResponse.builder()
                .shipmentCost(distance*shipmentMethod.getCostPerKm())
                .shipmentTime(adjustShipmentTime(travelDays +1,shipmentMethod))
                .shipmentMethod(shipmentMethod)
                .build();

    }

    @Override
    public List<ShipmentResponse> getAllShipmentServices() {
        return List.of();
    }

    @Override
    @KafkaListener(id = "consumer-order-created",topics = "order-created")
    public ShipmentModel createShipment(ShipmentModel shipmentModel) {
        return createShipmentService.createShipment(shipmentModel);
    }


    private Long adjustShipmentTime(long roundedDate,ShipmentMethod shipmentMethod){
        log.info(String.valueOf(roundedDate));
        switch (shipmentMethod){
            case FAST_DELIVER -> {
                return roundedDate + 1;

            }

            case STANDARD_DELIVER -> {
                return roundedDate + 2;
            }
        }
        return null;
    }


}
