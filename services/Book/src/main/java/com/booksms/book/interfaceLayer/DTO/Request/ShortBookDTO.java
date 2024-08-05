package com.booksms.book.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortBookDTO {
    private String title;
    private String name;
    private String categoryName;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    private int availableQuantity;
    private String image;
    private Integer chapter;
    private Boolean isInStock;
}
