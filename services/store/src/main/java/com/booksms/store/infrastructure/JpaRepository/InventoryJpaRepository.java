package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryJpaRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByAddressId(Integer id);
}
