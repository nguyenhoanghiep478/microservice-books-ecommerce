package com.booksms.store.infrastructure.repository;

import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import com.booksms.store.infrastructure.JpaRepository.InventoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryRepository implements IInventoryRepository {
    private final InventoryJpaRepository jpaRepository;

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
}
