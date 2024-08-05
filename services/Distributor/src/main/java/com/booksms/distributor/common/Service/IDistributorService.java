package com.booksms.distributor.common.Service;

import com.booksms.distributor.common.Data.DTO.DistributorDTO;

import java.util.List;

public interface IDistributorService {
    List<DistributorDTO> findAll();

    DistributorDTO findById(int id);

    DistributorDTO create(DistributorDTO request);

    DistributorDTO updateLicense(int id, int licenseId, DistributorDTO request);
}
