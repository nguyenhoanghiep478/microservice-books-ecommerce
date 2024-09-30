package com.booksms.store.web.controller;

import com.booksms.store.interfaceLayer.DTO.Request.CreateQuantityRequest;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateInventoryDTO;
import com.booksms.store.interfaceLayer.DTO.Response.BookResponseDTO;
import com.booksms.store.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.store.interfaceLayer.service.inventory.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final IInventoryService inventoryService;

    @PostMapping("/create-quantity")
    public ResponseEntity<?> createQuantity(@RequestBody CreateQuantityRequest request){
        BookResponseDTO response = inventoryService.createQuantity(request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("create quantity successful"))
                .status(200)
                .result(response)
                .build()
        );
    }

    @PostMapping("/sell-product-at-inventory/{id}")
    public ResponseEntity<?> sellProductAtInventory(@PathVariable int id, @RequestBody UpdateInventoryDTO request){
        inventoryService.updateInventory(id,request);

        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("update quantity successful"))
                .status(200)
                .build()
        );
    }

    @PostMapping("/stock-in/{id}")
    public ResponseEntity<?> stockIn(@PathVariable int id,@RequestBody UpdateInventoryDTO request){
         inventoryService.updateInventory(id,request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .message(List.of("update quantity successful"))
                .status(200)
                .build()
        );
    }
}
