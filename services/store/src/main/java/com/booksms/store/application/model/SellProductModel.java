package com.booksms.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellProductModel {
    private int quantity;
    private int bookId;
    private int inventoryId;
}
