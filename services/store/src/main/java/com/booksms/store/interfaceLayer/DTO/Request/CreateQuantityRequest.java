package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuantityRequest {
    private Integer quantity;
    private Integer bookId;
    private Integer inventoryId;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
}
