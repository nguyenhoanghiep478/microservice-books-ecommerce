package com.booksms.book.interfaceLayer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private Integer orderId;
    private Integer bookId;
    private String name;
    private BigDecimal price;
    private Integer totalQuantity;
}
