package com.booksms.store.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SellProductDTO {
    @NotNull(message = "id cannot null")
    private Integer id;
    @NotNull(message = "quantity cannot null")
    private int quantity;
}
