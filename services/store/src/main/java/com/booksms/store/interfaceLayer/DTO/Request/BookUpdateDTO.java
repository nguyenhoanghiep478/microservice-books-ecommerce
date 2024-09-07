package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookUpdateDTO {
    private Integer id;
    private BookUpdateInformationDTO updateInformation;
    private BookUpdateQuantityDTO updateQuantity;
}
