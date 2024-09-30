package com.booksms.shipment.interfaceLayer.dto.response;

import com.booksms.shipment.core.domain.state.ShipmentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentResponse {
    private Integer shipmentId;
    private ShipmentMethod shipmentMethod;
    private Long shipmentTime;
    private Double distance;
    private Double shipmentCost;
}
