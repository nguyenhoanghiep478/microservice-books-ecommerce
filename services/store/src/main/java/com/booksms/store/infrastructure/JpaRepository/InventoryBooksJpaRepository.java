package com.booksms.store.infrastructure.JpaRepository;

import com.booksms.store.core.domain.entity.InventoryBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryBooksJpaRepository extends JpaRepository<InventoryBook,Integer> {
}
