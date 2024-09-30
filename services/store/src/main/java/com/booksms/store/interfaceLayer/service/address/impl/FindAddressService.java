package com.booksms.store.interfaceLayer.service.address.impl;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.application.usecase.address.FindAddressUseCase;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.interfaceLayer.service.address.IFindAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAddressService implements IFindAddressService {
    private final FindAddressUseCase findAddressUseCase;


    @Override
    public List<String> findByids(List<Integer> ids) {
        List<Address> addresses = findAddressUseCase.execute(List.of(
                SearchCriteria.builder()
                        .key("id")
                        .operation("IN")
                        .value(ids)
                        .build()
        ));
        return addresses.stream().map(Address::toAddress).toList();
    }
}
