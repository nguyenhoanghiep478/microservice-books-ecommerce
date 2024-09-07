package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookUpdateInformationDTO {
    private String title;
    private String name;
    private Integer distributorId;
    private BigDecimal price;
    private String image;
    private Boolean isInStock;
    private Integer categoryId;
}
