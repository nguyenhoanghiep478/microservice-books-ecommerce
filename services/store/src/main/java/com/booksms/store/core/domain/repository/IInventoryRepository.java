package com.booksms.store.core.domain.repository;

import com.booksms.store.core.domain.entity.Inventory;

import java.util.Optional;

public interface IInventoryRepository {
    Optional<Inventory> findOneByAddressId(Integer addressId);

    Inventory save(Inventory inventory);

    Optional<Inventory> findById(Integer id);
}
