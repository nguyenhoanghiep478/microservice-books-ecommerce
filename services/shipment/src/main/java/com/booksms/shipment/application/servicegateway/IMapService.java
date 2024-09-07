package com.booksms.shipment.infrastructure.serviceGateway;

import java.util.List;
import java.util.Map;

public interface IMapService {
    List<Map<String, Object>> getCoordinates(String address);
    Map<String, Object> getRoute(double lat1, double lon1, double lat2, double lon2);
    Double getDistance(String from,String to);
}
