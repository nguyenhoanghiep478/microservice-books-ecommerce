package com.booksms.shipment.infrastructure.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "orsClient", url = "https://api.openrouteservice.org")
public interface DistanceClient {
    @GetMapping("/v2/directions/driving-car")
    Map<String, Object> getRoute(@RequestParam("api_key") String apiKey,
                                 @RequestParam("start") String startCoordinates,
                                 @RequestParam("end") String endCoordinates);
}
