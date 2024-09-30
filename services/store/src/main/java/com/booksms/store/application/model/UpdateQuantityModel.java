package com.booksms.store.application.model;

import com.booksms.store.interfaceLayer.DTO.Request.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuantityModel {
    private Integer inventoryId;
    private Integer bookId;
    private OrderType orderType;
    private Integer addOrMinusQuantity;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}
