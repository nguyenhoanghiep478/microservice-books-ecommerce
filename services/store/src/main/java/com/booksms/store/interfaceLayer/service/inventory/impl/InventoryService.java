package com.booksms.store.interfaceLayer.service.inventory.impl;

import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.Request.*;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.service.inventory.IInventoryService;
import com.booksms.store.interfaceLayer.service.inventory.IUpdateInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService {
    private final IUpdateInventoryService updateInventoryService;
    private final ModelMapper modelMapper;

    @Override
    public BookResponseDTO createQuantity(CreateQuantityRequest request) {
        Book book = updateInventoryService.createQuantity(request);

        return modelMapper.map(book, BookResponseDTO.class);
    }


    @Override
    public void updateInventory(Integer inventoryId, UpdateInventoryDTO updateInventoryDTO) {
        updateInventoryService.updateInventory(inventoryId,updateInventoryDTO);
    }
}
