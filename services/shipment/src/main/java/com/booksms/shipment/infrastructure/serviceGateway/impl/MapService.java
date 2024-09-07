package com.booksms.shipment.infrastructure.serviceGateway.impl;

import com.booksms.shipment.infrastructure.feignclient.DistanceClient;
import com.booksms.shipment.infrastructure.feignclient.MapClient;
import com.booksms.shipment.application.servicegateway.IMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MapService implements IMapService {
    private final DistanceClient distanceClient;
    private final MapClient mapClient;


    @Override
    public List<Map<String, Object>> getCoordinates(String address) {
        return mapClient.getCoordinates(address,"json",1);
    }

    @Override
    public Map<String, Object> getRoute(double lat1, double lon1, double lat2, double lon2) {
        String coordinates = lon1 + "," + lat1 + ";" + lon2 + "," + lat2;
        return distanceClient.getRoute(coordinates,"false");
    }

    @Override
    public Double getDistance(String from, String to) {
        List<Map<String,Object>> coords1 = getCoordinates(from);
        List<Map<String,Object>> coords2 = getCoordinates(to);

        double lat1 = Double.parseDouble((String) coords1.get(0).get("lat"));
        double lon1 = Double.parseDouble((String) coords1.get(0).get("lon"));
        double lat2 = Double.parseDouble((String) coords2.get(0).get("lat"));
        double lon2 = Double.parseDouble((String) coords2.get(0).get("lon"));

        Map<String, Object> route = getRoute(lat1, lon1, lat2, lon2);

        List<Map<String, Object>> routes = (List<Map<String, Object>>) route.get("routes");
        if (!routes.isEmpty()) {
            Double distance = (Double) routes.get(0).get("distance");
            return distance / 1000;
        }

        return  0.0;
    }
}
