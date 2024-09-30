package com.booksms.store.infrastructure.repository;

import com.booksms.store.application.model.SearchCriteria;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import com.booksms.store.infrastructure.JpaRepository.InventoryJpaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepository extends AbstractRepository<Inventory> implements IInventoryRepository {
    private final InventoryJpaRepository jpaRepository;

    public InventoryRepository(@Qualifier("inventoryClass") Class<Inventory> entityClass, InventoryJpaRepository jpaRepository) {
        super(entityClass);
        this.jpaRepository = jpaRepository;
    }


    @Override
    public Optional<Inventory> findOneByAddressId(Integer addressId) {
        return jpaRepository.findByAddressId(addressId);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return jpaRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> findById(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Inventory> findByCriteriaList(List<SearchCriteria> criteriaList) {
        return abstractSearch(criteriaList);
    }
}
