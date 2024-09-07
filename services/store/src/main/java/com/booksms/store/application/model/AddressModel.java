package com.booksms.store.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressModel {
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String zip;
}
