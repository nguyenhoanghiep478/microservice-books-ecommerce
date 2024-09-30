package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateInventoryDTO {
    private Integer inventoryId;
    private Integer bookId;
    private OrderType orderType;
    private Integer addOrMinusQuantity;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private Integer employeeId;
}
