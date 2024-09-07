package com.booksms.shipment.infrastructure.serviceGateway;

import com.booksms.shipment.infrastructure.feignclient.DistanceClient;
import com.booksms.shipment.infrastructure.feignclient.MapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapService {
    private final DistanceClient distanceClient;
    private final MapClient mapClient;


}
