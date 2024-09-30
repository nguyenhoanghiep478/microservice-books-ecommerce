package com.booksms.store.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", unique = true)
    private Address address;

    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL)
    private Set<InventoryBook> inventoryBooks;



    public void addInventoryBook(InventoryBook inventoryBook) {
        if (this.inventoryBooks == null) {
            this.inventoryBooks = new HashSet<>();
        }
        this.inventoryBooks.add(inventoryBook);
    }


    public InventoryBook getInventoryBookHaveBookId(Integer bookId){
        if(this.inventoryBooks == null){
            return null;
        }
       return inventoryBooks.stream()
               .filter(item -> item.isContainBookId(bookId))
               .findFirst()
               .orElse(null);
    }
}
