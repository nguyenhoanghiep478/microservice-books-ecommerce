package com.booksms.store.application.usecase.inventory;

import com.booksms.store.application.model.InventoryModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.exception.InventoryExistedException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateInventoryUseCase{
    private final IInventoryRepository inventoryRepository;

    public InventoryModel execute(InventoryModel inventoryModel){
       Optional<Inventory> inventory = inventoryRepository.findOneByAddressId(inventoryModel.getAddressId());
       if(inventory.isPresent()){
           throw new InventoryExistedException("Inventory already existed!");
       }

        Inventory entity = inventoryRepository.save(toInventory(inventoryModel));
        if(entity == null){
            throw new CreateFailureException("Create Inventory Failure");
        }

        return inventoryModel;
    }

    private Inventory toInventory(InventoryModel inventoryModel){
        Inventory inventory = new Inventory();
        Set<InventoryBook> inventoryBooks = new HashSet<>();
        inventory.setInventoryBooks(inventoryBooks);
        inventory.setName(inventoryModel.getName());
        inventory.setAddress(inventory.getAddress());
        return inventory;
    }


}
