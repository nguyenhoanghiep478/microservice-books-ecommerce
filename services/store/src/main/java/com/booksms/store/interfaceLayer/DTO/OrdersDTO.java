package com.booksms.store.interfaceLayer.DTO;

import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDTO {
    private List<OrderItemDTO> orderItems;
    private OrderType orderType;
}
