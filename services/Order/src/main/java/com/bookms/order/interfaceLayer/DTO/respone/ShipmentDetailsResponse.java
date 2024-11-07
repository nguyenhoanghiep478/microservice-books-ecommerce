package com.bookms.order.interfaceLayer.DTO.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDetailsResponse {

    private Integer id;

    private String originAddress;

    private String destinationAddress;

    private double distance;

    private String currentAddress;

    private Double totalFee;

    private String trackingNumber;

    private String shipmentServiceName;
}
