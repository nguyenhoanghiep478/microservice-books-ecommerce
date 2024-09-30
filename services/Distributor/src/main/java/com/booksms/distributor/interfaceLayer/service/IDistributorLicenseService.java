package com.booksms.distributor.interfaceLayer.service;

import com.booksms.distributor.interfaceLayer.DTO.LicenseDTO;

import java.util.List;

public interface IDistributorLicenseService {
    List<LicenseDTO> findAll();

    LicenseDTO findById(int id);

    LicenseDTO create(LicenseDTO request);

    LicenseDTO update(int id, LicenseDTO request);
}
