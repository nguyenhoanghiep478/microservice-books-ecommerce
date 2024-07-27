package com.booksms.customer.Customer.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
