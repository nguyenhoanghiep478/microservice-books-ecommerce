package com.booksms.store.application.usecase.address;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.core.domain.repository.IAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAddressUseCase {
    private final IAddressRepository addressRepository;

    public List<Address> execute(List<SearchCriteria> searchCriteria) {
        if(searchCriteria == null || searchCriteria.isEmpty()) {
            return addressRepository.findAll();
        }
        return addressRepository.findByCriteria(searchCriteria);
    }
}
