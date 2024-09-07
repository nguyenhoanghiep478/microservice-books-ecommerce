package com.booksms.book.application.model;

import com.booksms.book.interfaceLayer.DTO.Request.OrderType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {
    private Integer id;
    private String title;
    private String name;
    private Integer categoryId;
    private Integer distributorId;
    private BigDecimal price;
    private Integer availableQuantity;
    private MultipartFile image;
    private Integer chapter;
    private Boolean isInStock;
    private OrderType orderType;
}
