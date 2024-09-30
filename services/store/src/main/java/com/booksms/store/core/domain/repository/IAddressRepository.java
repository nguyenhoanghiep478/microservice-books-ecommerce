package com.booksms.store.core.domain.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Address;

import java.util.List;

public interface IAddressRepository {
    Address save(Address address);

    List<Address> findAll();

    List<Address> findByCriteria(List<SearchCriteria> searchCriteria);
}
