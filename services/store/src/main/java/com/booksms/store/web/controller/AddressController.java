package com.booksms.store.web.controller;

import com.booksms.store.application.model.AddressModel;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.interfaceLayer.DTO.Request.CreateAddressDTO;
import com.booksms.store.interfaceLayer.DTO.Response.CreateAddressResponse;
import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.service.address.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final IAddressService addressService;

    @GetMapping("/get-by-ids")
    public ResponseEntity<?> getAddressByIds(@RequestParam("ids") List<Integer> ids) {
        List<String> address = addressService.findByIds(ids);
        return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of("get address successful"))
                        .result(address)
                .build());
    }

    @PostMapping("/create-address")
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressDTO address) {
        AddressModel response = addressService.createAddress(address);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("create address successful"))
                .result(response.getId())
                .build());
    }
}
