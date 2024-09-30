package com.booksms.shipment.application.model;

import com.booksms.shipment.core.domain.state.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShipmentDetailModel {
    private Integer currentAddressId;
    private Status status;
}
