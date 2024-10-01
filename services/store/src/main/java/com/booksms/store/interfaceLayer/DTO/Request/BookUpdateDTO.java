package com.booksms.store.interfaceLayer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateDTO {
    private Integer id;
    private BookUpdateInformationDTO updateInformation;
    private BookUpdateQuantityDTO updateQuantity;
}