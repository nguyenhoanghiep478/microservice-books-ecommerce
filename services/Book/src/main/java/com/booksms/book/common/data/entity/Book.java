package com.booksms.book.common.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable_;

import java.math.BigDecimal;
import java.util.Date;

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
