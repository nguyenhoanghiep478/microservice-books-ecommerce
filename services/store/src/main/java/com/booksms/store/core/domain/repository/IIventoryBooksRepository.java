package com.booksms.store.core.domain.repository;

import com.booksms.store.core.domain.entity.InventoryBook;

public interface IIventoryBooksRepository {
    InventoryBook save(InventoryBook inventoryBook);
}
