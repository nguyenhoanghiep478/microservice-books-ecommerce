package com.booksms.book.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuantityDTO {
    @NotNull(message = "id cannot null")
    private Integer id;
    @NotNull(message = "quantity cannot null")
    private int quantity;
    @NotNull(message="type cannot null")
    private OrderType type;
}