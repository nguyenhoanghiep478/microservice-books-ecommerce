package com.booksms.store.infrastructure.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Address;
import com.booksms.store.core.domain.repository.IAddressRepository;
import com.booksms.store.infrastructure.JpaRepository.AddressJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository extends AbstractRepository<Address> implements IAddressRepository {
    private final AddressJpaRepository addressJpaRepository;

    AddressRepository(Class<Address> entityClass, AddressJpaRepository addressJpaRepository) {
        super(entityClass);
        this.addressJpaRepository = addressJpaRepository;
    }


    @Override
    public Address save(Address address) {
        return addressJpaRepository.save(address);
    }

    @Override
    public List<Address> findAll() {
        return addressJpaRepository.findAll();
    }

    @Override
    public List<Address> findByCriteria(List<SearchCriteria> searchCriteria) {
        return abstractSearch(searchCriteria);
    }
}
