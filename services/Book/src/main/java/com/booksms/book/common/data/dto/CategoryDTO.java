package com.booksms.book.common.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    @NotNull(message = "name is required for category")
    private String name;
    @NotNull(message = "description is required for category")
    private String description;
}
