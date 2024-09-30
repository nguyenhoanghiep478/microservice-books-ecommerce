package com.booksms.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentModel {
    private Integer id;
    private Integer originAddressId;
    private Integer destinationAddressId;
    private double distance;
    private Integer currentAddressId;
    private Double totalFee;
    private String trackingNumber;
    private Integer shipmentServiceId;
}
