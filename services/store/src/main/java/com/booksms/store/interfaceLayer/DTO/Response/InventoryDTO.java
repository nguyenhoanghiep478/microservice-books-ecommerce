package com.booksms.store.interfaceLayer.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Integer id;
    private String name;
    private Integer addressId;
}
