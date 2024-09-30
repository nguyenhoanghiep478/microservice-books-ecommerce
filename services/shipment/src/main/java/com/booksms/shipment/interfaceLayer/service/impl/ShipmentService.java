package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.application.model.ShipmentServiceModel;
import com.booksms.shipment.application.servicegateway.IMapService;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.infrastructure.serviceGateway.IAddressService;
import com.booksms.shipment.interfaceLayer.dto.request.UpdateShipmentDetailDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentDetailsResponse;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import com.booksms.shipment.interfaceLayer.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShipmentService implements IShipmentService {
    private final IMapService mapService;
    private final IFindShipmentService findShipmentService;
    private final IFindShipmentDetailService findShipmentDetailService;
    private final ICreateShipmentService createShipmentService;
    private final IAddressService addressService;
    private final IUpdateShipmentDetailService updateShipmentDetailService;

    @Override
    public ShipmentResponse getShipment(String from, String to, Integer id) {

        ShipmentServiceModel shipmentMethod = findShipmentService.findShipmentServiceById(id);

        return getShipmentsByShipmentService(from, to, List.of(shipmentMethod)).get(0);
    }


    private List<ShipmentResponse> getShipmentsByShipmentService(String from, String to, List<ShipmentServiceModel> shipmentServiceModels) {
        double distance = mapService.getDistance(from, to);
        List<ShipmentResponse> shipmentResponses = new ArrayList<>();
        for (ShipmentServiceModel shipmentMethod : shipmentServiceModels) {
            double travelHours = distance / shipmentMethod.getSpeed();
            int travelDays = (int) (travelHours / 24);
            shipmentResponses.add(
                    ShipmentResponse.builder()
                            .shipmentId(shipmentMethod.getId())
                            .distance(distance)
                            .shipmentCost(distance * shipmentMethod.getCostPerKm())
                            .shipmentTime(adjustShipmentTime(travelDays + 1, ShipmentMethod.valueOf(shipmentMethod.getName()), distance))
                            .shipmentMethod(ShipmentMethod.valueOf(shipmentMethod.getName()))
                            .build()
            );
        }
        return shipmentResponses;
    }

    @Override
    public List<ShipmentResponse> getAllShipmentServices() {
        return List.of();
    }

    @Override
    @KafkaListener(id = "consumer-create-shipment", topics = "create-shipment")
    public ShipmentModel createShipment(ShipmentModel shipmentModel) {
        log.info("Create shipment: {}", shipmentModel);
        return createShipmentService.createShipment(shipmentModel);
    }

    @Override
    public List<ShipmentResponse> getShipments(String from, String to) {
        List<ShipmentServiceModel> shipmentsModel = findShipmentService.findAll();
        return getShipmentsByShipmentService(from, to, shipmentsModel);
    }

    @Override
    public List<ShipmentDetailsResponse> getAllShipmentDetails() {
        List<ShipmentDetails> entities = findShipmentDetailService.getAll();

        return map(entities);
    }

    @Override
    public ShipmentDetailsResponse getById(Integer id) {
        ShipmentDetails entity = findShipmentDetailService.getById(id);
        return map(List.of(entity)).get(0);
    }

    @Override
    public ShipmentDetailsResponse updateById(Integer id, UpdateShipmentDetailDTO request) {
        ShipmentDetails updatedShipmentDetails = updateShipmentDetailService.updateById(id,request);

        return map(List.of(updatedShipmentDetails)).get(0);
    }


    private Long adjustShipmentTime(long roundedDate, ShipmentMethod shipmentMethod, double distance) {
        int extraDate = 0;
        if (distance > 0 && distance < 100) {
            extraDate = 2;
        } else if (distance > 100) {
            extraDate = 3;
        }

        switch (shipmentMethod) {
            case FAST_DELIVER -> {
                return roundedDate + extraDate + 1;

            }

            case STANDARD_DELIVER -> {
                return roundedDate + extraDate + 2;
            }
        }
        return null;
    }

    private List<ShipmentDetailsResponse> map(List<ShipmentDetails> entities) {
        Set<Integer> setDestinationIds = entities.stream().map(ShipmentDetails::getDestinationAddressId).collect(Collectors.toSet());
        Set<Integer> setCurrentAddressesIds = entities.stream().map(ShipmentDetails::getCurrentAddressId).collect(Collectors.toSet());
        Set<Integer> setOriginAddress = entities.stream().map(ShipmentDetails::getOriginAddressId).collect(Collectors.toSet());

        List<Integer> destinationIds = new ArrayList<>(setDestinationIds);
        List<Integer> currentAddressesIds = new ArrayList<>(setCurrentAddressesIds);
        List<Integer> originAddress = new ArrayList<>(setOriginAddress);

        HashMap<Integer,String> destinationAddresses = addressService.findByIds(destinationIds);
        HashMap<Integer,String> currentAddresses = addressService.findByIds(currentAddressesIds);
        HashMap<Integer,String> originAddresses = addressService.findByIds(originAddress);

        return entities.stream().map(entity -> ShipmentDetailsResponse.builder()
                        .shipmentServiceName(entity.getShipmentService().getName())
                        .id(entity.getId())
                        .currentAddress(currentAddresses.get(entity.getCurrentAddressId()))
                        .originAddress(originAddresses.get(entity.getOriginAddressId()))
                        .destinationAddress(destinationAddresses.get(entity.getDestinationAddressId()))
                        .status(entity.getStatus())
                        .totalFee(entity.getTotalFee())
                        .trackingNumber(entity.getTrackingNumber())
                        .distance(entity.getDistance())
                        .build())
                .toList();
    }
}
