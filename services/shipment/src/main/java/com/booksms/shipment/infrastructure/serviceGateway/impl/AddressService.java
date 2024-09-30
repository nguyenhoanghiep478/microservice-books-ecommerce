package com.booksms.shipment.infrastructure.serviceGateway.impl;

import com.booksms.shipment.infrastructure.feignclient.AddressClient;
import com.booksms.shipment.infrastructure.serviceGateway.IAddressService;
import com.booksms.shipment.interfaceLayer.dto.request.CreateAddressDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final AddressClient addressClient;
    private final ModelMapper modelMapper;

    @Override
    public HashMap<Integer,String> findByIds(List<Integer> ids) {
        ResponseEntity<ResponseDTO> responseEntity = addressClient.getAddressByIds(ids);
        ResponseDTO responseDTO = responseEntity.getBody();
        List<String> addresses =  modelMapper.map(responseDTO.getResult(),List.class);

        HashMap<Integer,String> map = new HashMap<>();
        AtomicInteger i = new AtomicInteger(0);
        addresses.forEach(address -> {
            map.put(ids.get(i.getAndIncrement()),address);
        });
        return map;
    }

    @Override
    public Integer createAdress(String destinationAddress) {
        ResponseEntity<ResponseDTO> responseEntity = addressClient.createAddress(CreateAddressDTO.builder()
                        .address(destinationAddress)
                .build());
        ResponseDTO responseDTO = responseEntity.getBody();
        return modelMapper.map(responseDTO.getResult(),Integer.class);
    }
}
