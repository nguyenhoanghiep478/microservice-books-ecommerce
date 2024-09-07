package com.booksms.shipment.infrastructure.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "nominatimClient",url = "https://nominatim.openstreetmap.org")
public interface MapClient {
    @GetMapping("/search")
    List<Map<String, Object>> getCoordinates(@RequestParam("q") String address,
                                             @RequestParam("format") String format,
                                             @RequestParam("limit") int limit);
}
