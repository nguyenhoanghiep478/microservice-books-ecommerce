package com.booksms.shipment.web.controller;

import com.booksms.shipment.core.domain.entity.ShipmentDetails;
import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.interfaceLayer.dto.request.UpdateShipmentDetailDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ResponseDTO;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentDetailsResponse;
import com.booksms.shipment.interfaceLayer.service.IShipmentService;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipment")
@RequiredArgsConstructor
public class ShipmentController {
    private final IShipmentService shipmentService;

    @GetMapping("/get-shipments")
    public List<ShipmentResponse> getShipmentTime(@RequestParam("from") String from, @RequestParam("to") String to) {
        return shipmentService.getShipments(from, to );
    }

    @GetMapping("/get-all-shipment-details")
    public ResponseEntity<?> getAllShipmentDetails() {
        List<ShipmentDetailsResponse> shipmentResponses = shipmentService.getAllShipmentDetails();
        return ResponseEntity.ok(ResponseDTO.builder()
                        .status(200)
                        .message(List.of("get all shipment details"))
                        .result(shipmentResponses)
                .build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getShipmentById(@PathVariable("id") Integer id) {
        ShipmentDetailsResponse response = shipmentService.getById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("get shipment details"))
                .result(response)
                .build());
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<?> updateShipmentDetail(@PathVariable("id") Integer id , UpdateShipmentDetailDTO request){
        ShipmentDetailsResponse response = shipmentService.updateById(id,request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(List.of("update shipment details"))
                .result(response)
                .build());
    }
}
