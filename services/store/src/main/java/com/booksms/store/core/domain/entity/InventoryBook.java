package com.booksms.store.core.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "inventory_book")
public class InventoryBook {
    @EmbeddedId
    private InventoryBookId id;

    @ManyToOne
    @MapsId("inventoryId")
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private Integer availableQuantity;

    public InventoryBook(Inventory inventory, Book book, Integer availableQuantity) {
        this.inventory = inventory;
        this.book = book;
        this.availableQuantity = availableQuantity;
        this.id = new InventoryBookId(inventory.getId(), book.getId());
    }

    public Integer getAvailableQuantity() {
        if(availableQuantity == null) {
            availableQuantity = 0;
        }
        return availableQuantity;
    }

    @Column(nullable = false)
    private BigDecimal salePrice;

    public InventoryBook() {

    }

    public boolean isContainBookId(Integer bookId){
        return book != null && book.getId().equals(bookId);
    }

}
