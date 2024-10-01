package com.booksms.shipment.interfaceLayer.dto.request;

import com.booksms.shipment.core.domain.state.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateShipmentDetailDTO {
    private String currentAddress;
    private Status status;
}
