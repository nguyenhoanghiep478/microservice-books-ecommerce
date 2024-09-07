package com.booksms.book.interfaceLayer.DTO.Request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookCreateDTO {
    private Integer id;
    private String title;
    private String name;
    private Integer chapter;
    private Integer availableQuantity;
    private Integer distributorId;
    private BigDecimal price;
    private String image;
    private Boolean isInStock = false;
    private Integer categoryId;
}
