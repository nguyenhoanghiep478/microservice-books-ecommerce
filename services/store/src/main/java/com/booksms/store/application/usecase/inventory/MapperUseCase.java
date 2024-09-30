package com.booksms.store.application.usecase.inventory;

import com.booksms.store.application.model.InventoryModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.entity.InventoryBook;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MapperUseCase {

    public Inventory toInventory(InventoryModel model){
        Inventory inventory = new Inventory();
        Set<InventoryBook> inventoryBooks = new HashSet<>();
        inventory.setInventoryBooks(inventoryBooks);
        inventory.setAddress(inventory.getAddress());
        inventory.setName(model.getName());
        return inventory;
    }

    public InventoryModel toInventoryModel(Inventory inventory){
        InventoryModel inventoryModel = new InventoryModel();
        inventoryModel.setName(inventory.getName());
        inventoryModel.setId(inventory.getId());
        inventoryModel.setAddressId(inventory.getAddress().getId());
        return inventoryModel;
    }

}
