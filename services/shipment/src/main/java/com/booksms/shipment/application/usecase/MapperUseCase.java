package com.booksms.shipment.application.usecase;

import com.booksms.shipment.application.model.ShipmentModel;
import com.booksms.shipment.application.model.ShipmentServiceModel;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.entity.ShipmentService;
import org.springframework.stereotype.Component;

@Component
public class MapperUseCase {

    public ShipmentServiceModel toModel(ShipmentService shipmentService){
        ShipmentServiceModel shipmentServiceModel = new ShipmentServiceModel();
        shipmentServiceModel.setId(shipmentService.getId());
        shipmentServiceModel.setName(shipmentService.getName());
        shipmentServiceModel.setSpeed(shipmentService.getSpeed());
        shipmentServiceModel.setCostPerKm(shipmentService.getCostPerKm());
        return shipmentServiceModel;
    }

    public ShipmentService toEntity(ShipmentServiceModel shipmentServiceModel){
        ShipmentService shipmentService = new ShipmentService();
        shipmentService.setName(shipmentServiceModel.getName());
        shipmentService.setSpeed(shipmentServiceModel.getSpeed());
        shipmentService.setCostPerKm(shipmentServiceModel.getCostPerKm());
        return shipmentService;

    }


    public ShipmentDetails toShipmentDetails(ShipmentModel shipmentModel, ShipmentService shipmentService){
        ShipmentDetails shipmentDetails = new ShipmentDetails();
        if(shipmentModel.getId() != null){
            shipmentDetails.setId(shipmentModel.getId());
        }
        shipmentDetails.setShipmentService(shipmentService);
        shipmentDetails.setDistance(shipmentModel.getDistance());
        shipmentDetails.setStatus(shipmentModel.getStatus());
        shipmentDetails.setTotalFee(shipmentModel.getTotalFee());
        shipmentDetails.setDestinationAddressId(shipmentModel.getDestinationAddressId());
        shipmentDetails.setOriginAddressId(shipmentModel.getOriginAddressId());
        shipmentDetails.setCurrentAddressId(shipmentModel.getCurrentAddressId());
        shipmentDetails.setTotalFee(shipmentModel.getTotalFee());
        shipmentDetails.setTrackingNumber(shipmentModel.getTrackingNumber());

        return shipmentDetails;
    }


    public ShipmentModel toShipmentModel(ShipmentDetails shipmentDetails){
        return ShipmentModel.builder()
                .id(shipmentDetails.getId())
                .currentAddressId(shipmentDetails.getCurrentAddressId())
                .destinationAddressId(shipmentDetails.getDestinationAddressId())
                .originAddressId(shipmentDetails.getOriginAddressId())
                .distance(shipmentDetails.getDistance())
                .status(shipmentDetails.getStatus())
                .totalFee(shipmentDetails.getTotalFee())
                .trackingNumber(shipmentDetails.getTrackingNumber())
                .build();




    }
}
