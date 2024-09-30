package com.booksms.store.application.usecase.inventory.UpdateUseCase;

import com.booksms.store.application.model.SellProductModel;
import com.booksms.store.application.model.UpdateQuantityModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.BookExpcetion.BookNotFoundException;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SellProductUseCase {
    private final IInventoryRepository inventoryRepository;

    public void execute(SellProductModel updateQuantityModel) {
        Inventory inventory = inventoryRepository.findById(updateQuantityModel.getInventoryId()).orElseThrow(
                () -> new InventoryNotExistedException("inventory not found")
        );

        InventoryBook inventoryBook = inventory.getInventoryBooks()
                .stream()
                .filter(item -> Objects.equals(item.getId().getBookId(), updateQuantityModel.getBookId()))
                .findFirst()
                .orElseThrow(
                        () -> new BookNotFoundException("book not found"));

        Integer currentQuantity = inventoryBook.getAvailableQuantity();
        Integer minusQuantity = updateQuantityModel.getQuantity();
        if(currentQuantity < minusQuantity) {
            throw new InSufficientQuantityException(String.format("Not enough %s for book id %s,current %s",minusQuantity,updateQuantityModel.getBookId(),currentQuantity));
        }

        inventoryBook.setAvailableQuantity(currentQuantity - minusQuantity);

        inventoryRepository.save(inventory);
    }

}
