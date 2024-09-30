package com.booksms.store.core.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class InventoryBookId implements Serializable {
    private Integer inventoryId;
    private Integer bookId;

    public InventoryBookId() {}

    public InventoryBookId(Integer inventoryId, Integer bookId) {
        this.inventoryId = inventoryId;
        this.bookId = bookId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryBookId that = (InventoryBookId) o;
        return Objects.equals(inventoryId, that.inventoryId) &&
                Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, bookId);
    }
}
