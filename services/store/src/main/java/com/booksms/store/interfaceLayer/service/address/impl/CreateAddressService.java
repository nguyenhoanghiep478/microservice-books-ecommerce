package com.booksms.store.interfaceLayer.service.address.impl;

import com.booksms.store.application.model.AddressModel;
import com.booksms.store.application.usecase.address.CreateAddressUseCase;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.interfaceLayer.service.address.ICreateAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAddressService implements ICreateAddressService {
    private final CreateAddressUseCase createAddressUseCase;
    private final ModelMapper modelMapper;


    @Override
    public AddressModel createAddress(AddressModel addressModel) {
        Address address = createAddressUseCase.createAddress(addressModel);
        return modelMapper.map(address, AddressModel.class);
    }
}
