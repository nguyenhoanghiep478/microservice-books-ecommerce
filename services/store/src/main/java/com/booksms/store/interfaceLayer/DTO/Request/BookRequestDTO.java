package com.booksms.store.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {
    private Integer id;
    @NotNull(message = "book title is required")
    private String title;
    @NotNull(message = "book name is required")
    private String name;
    @NotNull(message = "categoryId is required")
    private Integer categoryId;
    @NotNull(message = "distributorId is required")
    private Integer distributorId;
    @NotNull(message = "price is required")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    @NotNull(message = "available quantity is required")
    private Integer availableQuantity;
    @NotNull(message = "image for book is required")
    private MultipartFile image;
    @NotNull(message = "chapter is required to audit version")
    private Integer chapter;
    private Boolean isInStock;
    private OrderType orderType;
    private Integer inventoryId;
}
