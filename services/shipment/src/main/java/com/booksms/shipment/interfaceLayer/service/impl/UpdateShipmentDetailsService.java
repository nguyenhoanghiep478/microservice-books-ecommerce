package com.booksms.shipment.interfaceLayer.service.impl;

import com.booksms.shipment.application.model.UpdateShipmentDetailModel;
import com.booksms.shipment.application.usecase.UpdateShipmentDetailUseCase;
import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.infrastructure.serviceGateway.impl.AddressService;
import com.booksms.shipment.interfaceLayer.dto.request.UpdateShipmentDetailDTO;
import com.booksms.shipment.interfaceLayer.service.IUpdateShipmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UpdateShipmentDetailsService implements IUpdateShipmentDetailService {
    private final UpdateShipmentDetailUseCase updateShipmentDetailUseCase;
    private final AddressService addressService;
    private final KafkaTemplate<String,Integer> kafkaTemplate;

    @Override
    public ShipmentDetails updateById(Integer id, UpdateShipmentDetailDTO request) {
        Integer addressId = null;
        if(request.getCurrentAddress() != null && !request.getCurrentAddress().equals("")){
            addressId = addressService.createAdress(request.getCurrentAddress());
        }
        ShipmentDetails result = updateShipmentDetailUseCase.execute(id, UpdateShipmentDetailModel.builder()
                .currentAddressId(addressId)
                .status(request.getStatus())
                .build());

        if(Objects.equals(result.getDestinationAddressId(), result.getCurrentAddressId())){
            kafkaTemplate.send("shipped",result.getId());
        }

        return result;
    }
}
