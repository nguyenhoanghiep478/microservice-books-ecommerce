package com.booksms.shipment.infrastructure.serviceGateway.impl;

import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.infrastructure.serviceGateway.IMapService;
import com.booksms.shipment.infrastructure.serviceGateway.IShipmentService;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentService implements IShipmentService {
    private final IMapService mapService;


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
