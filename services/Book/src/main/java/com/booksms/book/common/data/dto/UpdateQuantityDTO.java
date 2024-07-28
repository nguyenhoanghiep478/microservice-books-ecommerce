package com.booksms.book.common.data.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuantityDTO {
    @NotNull(message = "quantity cannot null")
    @Size(min = 1)
    private int quantity;
    @NotNull(message="type cannot null")
    private OrderType type;
}
