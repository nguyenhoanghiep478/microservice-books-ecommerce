package com.booksms.store.interfaceLayer.service.inventory.impl;

import com.booksms.store.application.model.CreateQuantityModel;
import com.booksms.store.application.model.UpdateQuantityModel;
import com.booksms.store.application.servicegateway.IKafkaService;
import com.booksms.store.application.usecase.inventory.CreateUseCase.CreateQuantityUseCase;
import com.booksms.store.application.usecase.inventory.UpdateUseCase.UpdateQuantityUseCase;
import com.booksms.store.core.domain.entity.Book;
import com.booksms.store.interfaceLayer.DTO.OrderItemDTO;
import com.booksms.store.interfaceLayer.DTO.OrdersDTO;
import com.booksms.store.interfaceLayer.DTO.Request.*;
import com.booksms.store.interfaceLayer.service.inventory.IUpdateInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateInventoryService implements IUpdateInventoryService {
    private final UpdateQuantityUseCase updateQuantityUseCase;
    private final CreateQuantityUseCase createQuantityUseCase;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, OrdersDTO> orderKafkaTemplate;


    @Override
    public Book createQuantity(CreateQuantityRequest request) {
        return createQuantityUseCase.execute(modelMapper.map(request, CreateQuantityModel.class));
    }

    @Override
    public void sellProductAtInventory(Integer inventoryId, SellProductDTO request) {
        updateQuantityUseCase.execute(UpdateQuantityModel.builder()
                        .bookId(request.getId())
                        .addOrMinusQuantity(request.getQuantity())
                        .orderType(OrderType.SELL)
                        .inventoryId(inventoryId)
                .build());

    }

    @Override
    public void stockInProductAtInventory(int inventoryId, StockInDTO request) {
        updateQuantityUseCase.execute(UpdateQuantityModel.builder()
                        .bookId(request.getBookId())
                        .addOrMinusQuantity(request.getQuantity())
                        .salePrice(request.getSalePrice())
                        .orderType(OrderType.BUY)
                        .inventoryId(inventoryId)
                        .purchasePrice(request.getPurchasePrice())
                .build());
    }

    @Override
    public void updateInventory(Integer inventoryId, UpdateInventoryDTO updateInventoryDTO) {
        updateQuantityUseCase.execute(UpdateQuantityModel.builder()
                .bookId(updateInventoryDTO.getBookId())
                .addOrMinusQuantity(updateInventoryDTO.getAddOrMinusQuantity())
                .salePrice(updateInventoryDTO.getSalePrice())
                .orderType(updateInventoryDTO.getOrderType())
                .inventoryId(inventoryId)
                .purchasePrice(updateInventoryDTO.getPurchasePrice())
                .build());
        orderKafkaTemplate.send("stock-in",OrdersDTO.builder()
                        .inventoryId(inventoryId)
                        .orderType(OrderType.BUY)
                        .orderItems(List.of(
                                OrderItemDTO.builder()
                                        .bookId(updateInventoryDTO.getBookId())
                                        .totalQuantity(updateInventoryDTO.getAddOrMinusQuantity())
                                        .price(updateInventoryDTO.getSalePrice())
                                        .build()
                        ))
                        .customerId(updateInventoryDTO.getEmployeeId())
                .build());
    }
}
