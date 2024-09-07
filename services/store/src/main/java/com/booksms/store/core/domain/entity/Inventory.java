package com.booksms.book.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Entity
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToOne(mappedBy = "inventory")
    private Address address;

    @OneToMany(mappedBy = "inventory")
    private Set<InventoryBook> inventoryBooks;




}
