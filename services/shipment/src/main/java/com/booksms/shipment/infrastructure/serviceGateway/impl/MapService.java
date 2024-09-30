package com.booksms.shipment.infrastructure.serviceGateway.impl;

import com.booksms.shipment.infrastructure.feignclient.DistanceClient;
import com.booksms.shipment.infrastructure.feignclient.MapClient;
import com.booksms.shipment.application.servicegateway.IMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapService implements IMapService {
    private final DistanceClient distanceClient;
    private final MapClient mapClient;
    private final String apiKey = "5b3ce3597851110001cf62486b8a4632f43c4a0bbfe05b9a33504187";


    @Override
    public List<Map<String, Object>> getCoordinates(String address) {
        return mapClient.getCoordinates(address,"json",1);
    }

    @Override
    public Map<String, Object> getRoute(double lat1, double lon1, double lat2, double lon2) {
        try {
            // Tạo chuỗi tọa độ bắt đầu và kết thúc
            log.info("lat1: " + lat1 + " lon1: " + lon1);
            log.info("lat2: " + lat2 + " lon2: " + lon2);
            String startCoordinates = lon1 + "," + lat1;
            String endCoordinates = lon2 + "," + lat2;

            // Gọi API từ OpenRouteService
            Map<String, Object> routeResponse = distanceClient.getRoute(apiKey, startCoordinates, endCoordinates);

            // Kiểm tra nếu có dữ liệu về lộ trình
            if (routeResponse != null && routeResponse.containsKey("features")) {
                List<Map<String, Object>> routes = (List<Map<String, Object>>) routeResponse.get("features");
                if (!routes.isEmpty()) {
                    Map<String, Object> firstRoute = routes.get(0); // Lấy lộ trình đầu tiên

                    // Lấy "segments" từ lộ trình đầu tiên
                    if (firstRoute.containsKey("properties")) {
                        Map<String, Object> property = (Map<String, Object>) firstRoute.get("properties");
                        if (property != null) {

                            // Trích xuất khoảng cách (distance) từ segment
                            if (property.containsKey("summary")) {
                                Map<String, Object> summary = (Map<String, Object>) property.get("summary");
                                Double distance = (Double) summary.get("distance");
                                log.info("Distance: " + distance + " meters");

                                // Bạn có thể trả về thông tin segment và khoảng cách, hoặc trả về route đầy đủ
                                firstRoute.put("distance", distance); // Thêm distance vào kết quả
                                return firstRoute; // Trả về lộ trình đầu tiên kèm khoảng cách
                            }
                        }
                    }

                    return firstRoute; // Trả về lộ trình nếu không có segment
                }
            }

            // Trường hợp không tìm thấy lộ trình
            throw new IllegalArgumentException("No routes found for the given coordinates.");

        } catch (Exception e) {
            // Xử lý ngoại lệ và log lỗi
            throw new RuntimeException("Failed to get route: " + e.getMessage(), e);
        }
    }

    @Override
    public Double getDistance(String from, String to) {
        List<Map<String,Object>> coords1 = getCoordinates(from);
        List<Map<String,Object>> coords2 = getCoordinates(to);

        double lat1 = Double.parseDouble((String) coords1.get(0).get("lat"));
        double lon1 = Double.parseDouble((String) coords1.get(0).get("lon"));
        double lat2 = Double.parseDouble((String) coords2.get(0).get("lat"));
        double lon2 = Double.parseDouble((String) coords2.get(0).get("lon"));

        // Sử dụng ORS client để tính toán khoảng cách giữa hai điểm
        Map<String, Object> route = getRoute(lat1, lon1, lat2, lon2);
        if (route != null) {
            Double distance = (Double) route.get("distance");
            return distance / 1000; // Trả về khoảng cách theo km
        }

        return  0.0;
    }
}
