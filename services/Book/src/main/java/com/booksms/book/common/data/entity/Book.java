package com.booksms.book.common.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Book extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String name;
    private int chapter;
    private int availableQuantity;
    private BigDecimal price;
    private String image;
    private boolean isInStock = false;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    
}
