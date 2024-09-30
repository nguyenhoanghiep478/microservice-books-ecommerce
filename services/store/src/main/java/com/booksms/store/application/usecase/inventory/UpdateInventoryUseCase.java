package com.booksms.store.application.usecase.inventory;

import com.booksms.store.application.model.InventoryModel;
import com.booksms.store.core.domain.entity.Inventory;
import com.booksms.store.core.domain.exception.InventoryNotExistedException;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import com.booksms.store.core.domain.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateInventoryUseCase {
    private final IInventoryRepository inventoryRepository;

    public InventoryModel execute(final InventoryModel inventoryModel) {
        Inventory inventory = inventoryRepository.findById(inventoryModel.getId()).orElseThrow(
                () -> new InventoryNotExistedException("Inventory not existed")
        );


        inventory = merge(inventory,inventoryModel);

        Inventory updatedInventory = inventoryRepository.save(inventory);

        if(updatedInventory == null){
            throw new UpdateFailureException("Update Inventory Failure");
        }

        return inventoryModel;
    }

    private Inventory merge(final Inventory inventory, final InventoryModel inventoryModel) {
           if(inventoryModel.getName() != null){
               inventory.setName(inventoryModel.getName());
           }

          return inventory;

    }
}
