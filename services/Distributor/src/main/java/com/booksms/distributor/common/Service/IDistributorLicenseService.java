package com.booksms.distributor.common.Service;

import com.booksms.distributor.common.Data.DTO.DistributorDTO;
import com.booksms.distributor.common.Data.DTO.LicenseDTO;

import java.util.List;

public interface IDistributorLicenseService {
    List<LicenseDTO> findAll();

    LicenseDTO findById(int id);

    LicenseDTO create(LicenseDTO request);

    LicenseDTO update(int id, LicenseDTO request);
}
