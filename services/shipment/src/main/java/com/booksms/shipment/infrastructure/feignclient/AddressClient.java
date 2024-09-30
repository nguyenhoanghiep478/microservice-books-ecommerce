package com.booksms.shipment.infrastructure.feignclient;

import com.booksms.shipment.interfaceLayer.dto.ResponseDTO;
import com.booksms.shipment.interfaceLayer.dto.request.CreateAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "address-service",url = "http://localhost:5555/api/v1/address")
public interface AddressClient {

    @GetMapping("/get-by-ids")
    ResponseEntity<ResponseDTO> getAddressByIds(@RequestParam("ids") List<Integer> ids);

    @PostMapping("/create-address")
    ResponseEntity<ResponseDTO> createAddress(@RequestBody CreateAddressDTO address);

}
