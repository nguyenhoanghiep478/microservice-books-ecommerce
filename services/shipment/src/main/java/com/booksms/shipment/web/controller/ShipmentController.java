package com.booksms.shipment.web.controller;

import com.booksms.shipment.core.domain.state.ShipmentMethod;
import com.booksms.shipment.interfaceLayer.service.IShipmentService;
import com.booksms.shipment.interfaceLayer.dto.response.ShipmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shipment")
@RequiredArgsConstructor
public class ShipmentController {
    private final IShipmentService shipmentService;

    @GetMapping("/get-shipment-time")
    public ShipmentResponse getShipmentTime(@RequestParam("from") String from, @RequestParam("to") String to) {
        return shipmentService.getShipment(from, to , ShipmentMethod.FAST_DELIVER);
    }
}
