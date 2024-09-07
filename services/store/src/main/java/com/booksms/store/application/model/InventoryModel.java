package com.booksms.store.application.model;

import com.booksms.store.core.domain.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryModel {
    private Integer id;
    private String name;
    private Integer addressId;
}
