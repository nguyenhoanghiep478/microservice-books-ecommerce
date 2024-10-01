package com.booksms.store.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryDTO {
    private Integer id;
    private @NotNull(message = "name is required for category")
    String name;
    private @NotNull(message = "description is required for category")
    String description;
}
