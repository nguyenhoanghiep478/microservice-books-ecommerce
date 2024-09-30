package com.booksms.distributor.interfaceLayer.service;

import com.booksms.distributor.interfaceLayer.DTO.DistributorDTO;

import java.util.List;

public interface IDistributorService {
    List<DistributorDTO> findAll();

    DistributorDTO findById(int id);

    DistributorDTO create(DistributorDTO request);

    DistributorDTO updateLicense(int id, int licenseId, DistributorDTO request);
}
