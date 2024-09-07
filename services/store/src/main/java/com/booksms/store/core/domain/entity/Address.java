package com.booksms.store.core.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;
    private String zip;
    @OneToOne(mappedBy = "address",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Inventory inventory;

    public String toAddress(){
        return street + " " + city + " " + state + " " + zip;
    }
}


