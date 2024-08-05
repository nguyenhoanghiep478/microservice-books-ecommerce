package com.booksms.distributor.common.Data.Repository;

import com.booksms.distributor.common.Data.Entity.DistributorLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistributorLicenseRepository extends JpaRepository<DistributorLicense, Integer> {
    DistributorLicense findDistributorLicenseByLicenseNumber(String licenseNumber);
}
