package com.booksms.book.common.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class BookDTO {
    @NotNull(message = "book title is required")
    private String title;
    @NotNull(message = "book name is required")
    private String name;
    @NotNull(message = "categoryId is required")
    private int categoryId;
    @NotNull(message = "price is required")
    @Size(min = 0)
    private BigDecimal price;
    @NotNull(message = "available quantity is required")
    @Size(min = 0)
    @JsonIgnore
    private int availableQuantity;
    @NotNull(message = "image for book is required")
    private String image;
}
