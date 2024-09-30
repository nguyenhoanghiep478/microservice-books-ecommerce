package com.booksms.store.interfaceLayer.service.inventory;

import com.booksms.store.interfaceLayer.DTO.Request.CreateQuantityRequest;
import com.booksms.store.interfaceLayer.DTO.Request.SellProductDTO;
import com.booksms.store.interfaceLayer.DTO.Request.StockInDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateInventoryDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;

public interface IInventoryService {
    BookResponseDTO createQuantity(CreateQuantityRequest request);


    void updateInventory(Integer inventoryId, UpdateInventoryDTO updateInventoryDTO);
}
