package com.booksms.store.application.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StockInModel {
    private int inventoryId;
    private int bookId;
    private int quantity;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}
