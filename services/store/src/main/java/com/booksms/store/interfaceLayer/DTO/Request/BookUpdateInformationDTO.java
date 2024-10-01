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
public class BookUpdateInformationDTO {
    private String title;
    private String name;
    private Integer distributorId;
    private BigDecimal price;
    private String image;
    private Boolean isInStock;
    private Integer categoryId;
}
