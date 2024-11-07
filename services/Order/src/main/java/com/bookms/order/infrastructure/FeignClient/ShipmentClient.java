package com.bookms.order.infrastructure.FeignClient;

import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shipment-service",url = "http://localhost:5560/api/v1/shipment")
public interface ShipmentClient {
    @GetMapping("/get-by-id/{id}")
    ResponseEntity<ResponseDTO> getShipmentById(@PathVariable("id") Integer id);
}
