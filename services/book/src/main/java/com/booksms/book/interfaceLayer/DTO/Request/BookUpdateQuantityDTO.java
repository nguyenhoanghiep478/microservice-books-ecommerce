package com.booksms.book.interfaceLayer.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookUpdateQuantityDTO {
    private String name;
    private Integer availableQuantity;
}
