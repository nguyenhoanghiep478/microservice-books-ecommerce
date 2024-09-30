package com.booksms.store.interfaceLayer.service.inventory;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.Request.CreateQuantityRequest;
import com.booksms.store.interfaceLayer.DTO.Request.SellProductDTO;
import com.booksms.store.interfaceLayer.DTO.Request.StockInDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateInventoryDTO;

public interface IUpdateInventoryService {
    Book createQuantity(CreateQuantityRequest request);

    void sellProductAtInventory(Integer inventoryId, SellProductDTO request);

    void stockInProductAtInventory(int inventoryId, StockInDTO request);

    void updateInventory(Integer inventoryId, UpdateInventoryDTO updateInventoryDTO);
}
