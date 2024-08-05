package com.booksms.distributor.common.Service.impl;

import com.booksms.distributor.common.Data.DTO.LicenseDTO;
import com.booksms.distributor.common.Data.Entity.Distributor;
import com.booksms.distributor.common.Data.Entity.DistributorLicense;
import com.booksms.distributor.common.Data.GenericMapper;
import com.booksms.distributor.common.Data.Repository.DistributorLicenseRepository;
import com.booksms.distributor.common.Data.Repository.DistributorRepository;
import com.booksms.distributor.common.Service.IDistributorLicenseService;
import com.booksms.distributor.start.Config.Exception.DistributorNotFoundException;
import com.booksms.distributor.start.Config.Exception.LicenseExistedException;
import com.booksms.distributor.start.Config.Exception.LicenseNotFoundException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributorLicenseService implements IDistributorLicenseService {
    private final DistributorLicenseRepository repository;
    private final DistributorRepository distributorRepository;
    private final GenericMapper<DistributorLicense, LicenseDTO,LicenseDTO> licenseMapper;

    @Override
    public List<LicenseDTO> findAll() {
        List<DistributorLicense> distributorLicenses = repository.findAll();
        if(distributorLicenses.isEmpty()) {
            throw new InternalServerErrorException("something went wrong,please try again");
        }
        List<LicenseDTO> licenseDTOs = licenseMapper.toListResponse(distributorLicenses,LicenseDTO.class);
        if(licenseDTOs.isEmpty()) {
            throw new InternalServerErrorException("something went wrong,please try again");
        }
        return licenseDTOs;
    }

    @Override
    public LicenseDTO findById(int id) {
        DistributorLicense distributorLicense = repository.findById(id).orElseThrow(
                    () -> new LicenseNotFoundException(String.format("License with id %s not found",id))
        );
        LicenseDTO licenseDTO = licenseMapper.toResponse(distributorLicense,LicenseDTO.class);
        if(licenseDTO == null) {
            throw new InternalServerErrorException("something went wrong,please try again");
        }
        return licenseDTO;
    }

    @Override
    @Transactional
    public LicenseDTO create(LicenseDTO request) {
        if(request.getLicenceNumber()  != null){
            DistributorLicense distributorLicense = repository.findDistributorLicenseByLicenseNumber(request.getLicenceNumber());
            if(distributorLicense != null){
                throw new LicenseExistedException("License already existed");
            }
        }
        DistributorLicense license = licenseMapper.toEntity(request,DistributorLicense.class);
        Distributor distributor = distributorRepository.findDistributorById(request.getDistributorId()).orElseThrow(
                () -> new DistributorNotFoundException("Distributor with id " + request.getDistributorId() + " not found")
        );
        license.setDistributor(distributor);
        DistributorLicense result = repository.save(license);
        LicenseDTO licenseDTO = licenseMapper.toResponse(result,LicenseDTO.class);
        return licenseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LicenseDTO update(int id, LicenseDTO request) {
        DistributorLicense distributorLicense = repository.findById(id).orElseThrow(
                () -> new LicenseNotFoundException(String.format("License with id %s not found",id))
        );
        mergeUpdate(distributorLicense, request);
        DistributorLicense license =  repository.save(distributorLicense);
        return licenseMapper.toResponse(license,LicenseDTO.class);
    }
    private DistributorLicense mergeUpdate(DistributorLicense license, LicenseDTO updateRequest) {
        if(updateRequest.getLicenceNumber() != null){
            license.setLicenseNumber(updateRequest.getLicenceNumber());
        }
        if(updateRequest.getLicenceExpiryDate() != null){
            license.setLicenceExpiryDate(updateRequest.getLicenceExpiryDate());
        }
        if(updateRequest.getAccountId() != null){
            license.setAccountId(updateRequest.getAccountId());
        }
        if(updateRequest.getLicenceType() != null){
            license.setLicenseType(updateRequest.getLicenceType());
        }
        return license;
    }
    private boolean existByLicenseNumber(String licenseNumber) {
        return repository.findDistributorLicenseByLicenseNumber(licenseNumber) != null;
    }
}
