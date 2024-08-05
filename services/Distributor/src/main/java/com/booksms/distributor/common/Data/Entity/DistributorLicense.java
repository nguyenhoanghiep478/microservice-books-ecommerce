package com.booksms.distributor.common.Data.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Entity
@Setter
public class DistributorLicense extends AbstractEntity{
    @Id
    private Integer id;
    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;
    private Timestamp licenceExpiryDate;
    private Integer accountId;
    @OneToOne(mappedBy = "distributorLicense")
    @JoinColumn(nullable = false,name = "distributor_id")
    private Distributor distributor;

}
