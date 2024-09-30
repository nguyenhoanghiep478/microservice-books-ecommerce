package com.booksms.distributor.interfaceLayer.DTO;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenseDTO {
    private Long id;
    private String licenceNumber;
    private String licenceType;
    private Timestamp licenceExpiryDate;
    private Integer accountId;
    private Integer distributorId;
}
