package com.booksms.distributor.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product  extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;

    @OneToMany(mappedBy = "product")
    private Set<DistributorProduct> distributorProducts;

    private String name;
    private String description;
    private BigDecimal purchasePrice;

}
