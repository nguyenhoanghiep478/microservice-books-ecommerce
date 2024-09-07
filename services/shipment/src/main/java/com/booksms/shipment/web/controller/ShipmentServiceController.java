package com.booksms.shipment.web.controller;

import com.booksms.shipment.interfaceLayer.dto.ResponseDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import com.booksms.shipment.interfaceLayer.service.IShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipmentService")
@RequiredArgsConstructor
public class ShipmentServiceController {
    private final IShipmentService shipmentService;
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllShipmentServices() {
        List<ShipmentResponse> response = shipmentService.getAllShipmentServices();
        return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of("get all shipment services successfully"))
                        .result(response)
                .build());
    }
}
