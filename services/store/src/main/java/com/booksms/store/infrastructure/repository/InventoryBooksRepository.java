package com.booksms.store.infrastructure.repository;

import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.repository.IIventoryBooksRepository;
import com.booksms.store.infrastructure.JpaRepository.InventoryBooksJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryBooksRepository implements IIventoryBooksRepository {
    private final InventoryBooksJpaRepository inventoryBooksJpaRepository;

    @Override
    public InventoryBook save(InventoryBook inventoryBook) {
        return inventoryBooksJpaRepository.save(inventoryBook);
    }
}
