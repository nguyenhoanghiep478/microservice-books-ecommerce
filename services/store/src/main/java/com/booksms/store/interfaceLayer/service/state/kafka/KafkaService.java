package com.booksms.store.interfaceLayer.service.state.kafka;

import com.booksms.store.application.servicegateway.IKafkaService;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.interfaceLayer.DTO.OrderItemDTO;
import com.booksms.store.interfaceLayer.DTO.OrdersDTO;
import com.booksms.store.interfaceLayer.DTO.Request.UpdateInventoryDTO;
import com.booksms.store.interfaceLayer.DTO.ResponseOrderCreated;
import com.booksms.store.interfaceLayer.service.book.IBookService;
import com.booksms.store.interfaceLayer.service.inventory.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaService implements IKafkaService {
    private final KafkaTemplate<String, ResponseOrderCreated> kafkaTemplate;
    private final IBookService bookService;
    private final IInventoryService inventoryService;


    @KafkaListener(id = "consumer-created-order", topics = "order-created")
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(OrdersDTO ordersDTO) {
        try {
            for (OrderItemDTO item : ordersDTO.getOrderItems()) {
               inventoryService.updateInventory(ordersDTO.getInventoryId(), UpdateInventoryDTO.builder()
                       .bookId(item.getBookId())
                       .addOrMinusQuantity(item.getTotalQuantity())
                       .salePrice(item.getPrice())
                       .orderType(ordersDTO.getOrderType())
                       .inventoryId(ordersDTO.getInventoryId())
                       .purchasePrice(item.getPurchasePrice())
                       .build());
            }

            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                    .serviceName("BookService")
                    .message("successful")
                    .build());


        } catch (InSufficientQuantityException e) {
            kafkaTemplate.send("order-created-response", ResponseOrderCreated.builder()
                    .serviceName("BookService")
                    .message(e.getMessage())
                    .build());
        }


    }
}
