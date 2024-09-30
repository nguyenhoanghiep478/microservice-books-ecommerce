package com.booksms.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuantityModel {
    private Integer bookId;
    private Integer quantity;
    private Integer inventoryId;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}
