package com.booksms.customer.Customer.data;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Validated
public class Address {
    private String street;
    private String city;
    private String district;
    private String ward;
    private String zipCode;
}
