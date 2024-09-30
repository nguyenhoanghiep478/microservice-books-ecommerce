package com.booksms.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersModel {
    private Integer shipmentId;
    private Integer paymentId;
    private Integer shipmentServiceId;
    private Integer originAddressId;
    private String destinationAddress;
    private double distance;
    private Double shipmentFee;
}
