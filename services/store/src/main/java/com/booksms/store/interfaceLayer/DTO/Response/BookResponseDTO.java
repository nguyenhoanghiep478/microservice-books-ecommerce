package com.booksms.store.interfaceLayer.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDTO {
    private Integer id;
    private String title;
    private String name;
    private Integer categoryId;
    private Integer distributorId;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Integer availableQuantity;
    private String image;
    private Integer chapter;
    private Boolean isInStock;
    private Date createdDate;
    private Date lastModifiedDate;
    private Set<InventoryDTO> inventory;
}
