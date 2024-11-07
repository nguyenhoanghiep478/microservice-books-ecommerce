package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.infrastructure.FeignClient.ShipmentClient;
import com.bookms.order.infrastructure.serviceGateway.IShipmentServiceGateway;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import com.bookms.order.interfaceLayer.DTO.respone.ShipmentDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceGateway implements IShipmentServiceGateway {
    private final ShipmentClient shipmentClient;
    private final ModelMapper modelMapper;

    @Override
    public ShipmentDetailsResponse findShipmentById(Integer shipmentId) {
        ResponseEntity<ResponseDTO> resp = shipmentClient.getShipmentById(shipmentId);
        ResponseDTO responseDTO = resp.getBody();
        if(responseDTO.getResult() == null){
            return null;
        }
        return modelMapper.map(responseDTO.getResult(), ShipmentDetailsResponse.class);
    }
}
