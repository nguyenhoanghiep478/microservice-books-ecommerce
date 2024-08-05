package com.booksms.distributor.common.Data.DTO;

import com.booksms.distributor.common.Data.Entity.LicenseType;
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
    private LicenseType licenceType;
    private Timestamp licenceExpiryDate;
    private Integer accountId;
    private Integer distributorId;
}
