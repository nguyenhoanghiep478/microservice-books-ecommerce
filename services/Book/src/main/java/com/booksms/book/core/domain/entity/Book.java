package com.booksms.book.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Book extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String name;
    private Integer chapter;
    private Integer availableQuantity;
    private Integer distributorId;
    private BigDecimal price;
    private String image;
    private Boolean isInStock = false;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    
}
