package com.booksms.distributor.common.Service.impl;

import com.booksms.distributor.common.Data.DTO.DistributorDTO;
import com.booksms.distributor.common.Data.Entity.Distributor;
import com.booksms.distributor.common.Data.Entity.DistributorLicense;
import com.booksms.distributor.common.Data.GenericMapper;
import com.booksms.distributor.common.Data.Repository.DistributorLicenseRepository;
import com.booksms.distributor.common.Data.Repository.DistributorRepository;
import com.booksms.distributor.common.Service.IDistributorService;
import com.booksms.distributor.start.Config.Exception.DistributorExistedException;
import com.booksms.distributor.start.Config.Exception.DistributorNotFoundException;
import com.booksms.distributor.start.Config.Exception.LicenseNotFoundException;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistributorService implements IDistributorService {
    private final DistributorRepository distributorRepository;
    private final DistributorLicenseRepository distributorLicenseRepository;
    private final GenericMapper<Distributor,DistributorDTO,DistributorDTO> distributorMapper;
    @Override
    public List<DistributorDTO> findAll() {
        List<Distributor> distributors = distributorRepository.findAll();
        if(distributors.isEmpty()){
            throw new InternalServerErrorException("something wrong,please try again");
        }
        List<DistributorDTO> distributorDTOS = distributorMapper.toListResponse(distributors,DistributorDTO.class);
        if(distributors.isEmpty()){
            throw new InternalServerErrorException("something wrong,please try again");
        }
        return distributorDTOS;
    }

    @Override
    public DistributorDTO findById(int id) {
        Distributor distributor = distributorRepository.findById(id).orElseThrow(
                ()->    new DistributorNotFoundException(String.format("distributor with id %s not found",id))
        );
        DistributorDTO distributorDTO = distributorMapper.toResponse(distributor,DistributorDTO.class);
        if(distributorDTO == null){
            throw new InternalServerErrorException("something wrong,please try again");
        }
        return distributorDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DistributorDTO create(DistributorDTO request) {
        if(existByName(request.getName())){
            throw new DistributorExistedException(String.format("Distributor with name %s already existed",request.getName()));
        }
        Distributor distributor = distributorMapper.toEntity(request,Distributor.class);
        distributor = distributorRepository.save(distributor);
        DistributorDTO distributorDTO = distributorMapper.toResponse(distributor,DistributorDTO.class);
        if(distributorDTO == null){
            throw new InternalServerErrorException("something wrong,please try again");
        }
        return distributorDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DistributorDTO updateLicense(int id, int licenseId, DistributorDTO request) {
        DistributorLicense license =  distributorLicenseRepository.findById(licenseId).orElseThrow(
                () -> new LicenseNotFoundException(String.format("license with id %s not found",licenseId))
        );
        Distributor distributor = distributorRepository.findById(id).orElseThrow(
                () -> new DistributorNotFoundException(String.format("distributor with id %s not found",id))
        );
        distributor.setId(licenseId);
        distributor = distributorRepository.save(distributor);
        DistributorDTO distributorDTO = distributorMapper.toResponse(distributor,DistributorDTO.class);
        if(distributorDTO == null){
            throw new InternalServerErrorException("something wrong,please try again");
        }
        return distributorDTO;
    }

    private boolean existById(int id) {
        return distributorRepository.findById(id).isPresent();
    }
    private boolean existByName(String name) {
        return distributorRepository.findDistributorByName(name).isPresent();
    }
}
