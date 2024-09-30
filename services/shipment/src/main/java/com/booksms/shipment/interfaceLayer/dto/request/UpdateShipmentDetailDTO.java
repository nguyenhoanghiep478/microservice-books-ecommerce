package com.booksms.shipment.interfaceLayer.dto.request;

import com.booksms.shipment.core.domain.state.Status;
import lombok.Data;

@Data
public class UpdateShipmentDetailDTO {
    private String currentAddress;
    private Status status;
}
