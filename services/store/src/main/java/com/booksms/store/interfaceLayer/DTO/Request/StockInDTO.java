package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInDTO {
    private int inventoryId;
    private int bookId;
    private int quantity;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}
