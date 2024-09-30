package com.booksms.store.application.usecase.address;

import com.booksms.store.application.model.AddressModel;
import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.core.domain.repository.IAddressRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CreateAddressUseCase {
    private final IAddressRepository addressRepository;
    private final FindAddressUseCase findAddressUseCase;

    public Address createAddress(AddressModel address) {
        Address addressEntity = map(address);
        Address existedAddress = getExistedAddress(addressEntity);
        if (existedAddress != null) {
            return existedAddress;
        }
        return addressRepository.save(addressEntity);
    }

    private Address getExistedAddress(Address address) {
        List<Address> existedAddress = findAddressUseCase.execute(List.of(
                SearchCriteria.builder()
                        .key("city")
                        .operation(":")
                        .value(address.getCity())
                        .build(),
                SearchCriteria.builder()
                        .key("state")
                        .operation(":")
                        .value(address.getState())
                        .build(),
                SearchCriteria.builder()
                        .key("street")
                        .operation(":")
                        .value(address.getStreet())
                        .build()

        ));

        if(!existedAddress.isEmpty()) {
            return existedAddress.get(0);
        }
        return null;
    }

    private Address map(AddressModel address) {
        Address addressEntity = new Address();
        if(address.getId() != null){
            addressEntity.setId(address.getId());
        }
        addressEntity.setStreet(address.getStreet());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setZip(address.getZip());

        return addressEntity;
    }
}
