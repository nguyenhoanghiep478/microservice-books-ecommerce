package com.booksms.store.application.usecase.inventory.UpdateUseCase;

import com.booksms.store.application.model.UpdateQuantityModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.BookException.BookNotFoundException;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReStockAfterCancelUseCase {
    private final IInventoryRepository inventoryRepository;

    public void execute(UpdateQuantityModel updateQuantityModel) {
       Inventory inventory = inventoryRepository.findById(updateQuantityModel.getInventoryId()).orElseThrow(
               () -> new InventoryNotExistedException(String.format("Inventory with id %s not found", updateQuantityModel.getInventoryId()))
       );

        InventoryBook inventoryBook = inventory.getInventoryBookHaveBookId(updateQuantityModel.getBookId());
        if(inventoryBook == null){
            throw new BookNotFoundException(String.format("Book with id %s not found", updateQuantityModel.getBookId()));
        }
        inventoryBook.setAvailableQuantity(updateQuantityModel.getAddOrMinusQuantity() + inventoryBook.getAvailableQuantity());

        inventoryRepository.save(inventory);
    }
}
