package com.booksms.shipment.application.usecase;

import com.booksms.shipment.application.model.Criteria;
import com.booksms.shipment.application.model.UpdateShipmentDetailModel;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.repository.IShipmentDetailRepository;
import com.booksms.shipment.core.domain.state.Status;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateShipmentDetailUseCase {
    private final IShipmentDetailRepository shipmentDetailRepository;
    private final FindShipmentDetailsUseCase findShipmentDetailsUseCase;

    public ShipmentDetails execute(Integer id, UpdateShipmentDetailModel model) {
        ShipmentDetails shipmentDetails = findShipmentDetailsUseCase.execute(List.of(
                Criteria.builder()
                        .key("id")
                        .operator(":")
                        .value(id)
                        .build()
        )).get(0);

        if(shipmentDetails == null) {
            throw new BadRequestException("Shipment details not found");
        }

        ShipmentDetails mergedShipmentDetail = merge(shipmentDetails,model);

        return shipmentDetailRepository.save(mergedShipmentDetail);
    }

    private ShipmentDetails merge(ShipmentDetails shipmentDetails, UpdateShipmentDetailModel model) {
        if(model.getStatus()!= null && model.getStatus().equals(Status.SHIPPED)){
            shipmentDetails.setCurrentAddressId(shipmentDetails.getDestinationAddressId());
            shipmentDetails.setStatus(Status.SHIPPED);
            return shipmentDetails;
        }
        if(model.getCurrentAddressId() != null && model.getCurrentAddressId().equals(shipmentDetails.getDestinationAddressId())){
            shipmentDetails.setCurrentAddressId(model.getCurrentAddressId());
            shipmentDetails.setStatus(Status.SHIPPED);
            return shipmentDetails;
        }

        if(model.getStatus() != null){
            shipmentDetails.setStatus(model.getStatus());
        }

        if(model.getCurrentAddressId() != null){
            shipmentDetails.setCurrentAddressId(model.getCurrentAddressId());
        }

        return shipmentDetails;
    }
}
