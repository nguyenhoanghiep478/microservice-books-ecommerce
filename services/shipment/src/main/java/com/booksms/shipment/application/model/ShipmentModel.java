package com.booksms.shipment.application.model;

import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.core.domain.state.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentModel {
    private Integer id;
    private Integer originAddressId;
    private Integer destinationAddressId;
    private double distance;
    private Integer currentAddressId;
    private Double totalFee;
    private String trackingNumber;
    private Status status;
    private Integer shipmentServiceId;
}
