package com.booksms.store.application.usecase.Book.CreateUseCase;

import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.entity.InventoryBookId;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IIventoryBooksRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateInventoryBookUseCase {
    private final IIventoryBooksRepository inventoryBooksRepository;

    public InventoryBook execute(InventoryBook inventoryBook) {

        if(inventoryBook.getInventory() == null){
            throw new InventoryNotExistedException("Inventory not exist");
        }
        if(inventoryBook.getAvailableQuantity() == 0){
            throw new BadRequestException("Available quantity is 0");
        }

        InventoryBookId inventoryBookId = new InventoryBookId();
        inventoryBookId.setInventoryId(inventoryBook.getInventory().getId());
        inventoryBookId.setBookId(inventoryBook.getBook().getId());

        inventoryBook.setId(inventoryBookId);

        return inventoryBooksRepository.save(inventoryBook);
    }
}
