package com.booksms.distributor.core.domain.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Table(name = "distributor")
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;

    private String name;

    private String slug;

    private Integer addressId;


    @OneToMany(mappedBy = "distributor")
    private Set<DistributorProduct> distributorProduct;
}
