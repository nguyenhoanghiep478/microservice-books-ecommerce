package com.booksms.shipment.infrastructure.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "osrmClient", url = "http://router.project-osrm.org")
public interface DistanceClient {
    @GetMapping("/route/v1/driving/{coordinates}")
    Map<String, Object> getRoute(@PathVariable("coordinates") String coordinates,
                                 @RequestParam("overview") String overview);
}
