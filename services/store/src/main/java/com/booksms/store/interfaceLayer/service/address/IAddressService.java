package com.booksms.store.interfaceLayer.service.address;

import com.booksms.store.application.model.AddressModel;
import com.booksms.store.interfaceLayer.DTO.Request.CreateAddressDTO;
import com.booksms.store.interfaceLayer.DTO.Response.CreateAddressResponse;

import java.util.List;

public interface IAddressService {
    List<String> findByIds(List<Integer> ids);

    AddressModel createAddress(CreateAddressDTO address);
}
