package com.booksms.shipment.interfaceLayer.dto.response;

import com.booksms.shipment.core.domain.entity.ShipmentService;
import com.booksms.shipment.core.domain.state.Status;
import jakarta.persistence.*;
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

    private Status status;

    private String shipmentServiceName;
}
