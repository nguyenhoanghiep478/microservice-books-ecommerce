package com.booksms.customer.Customer.common.service;

import com.booksms.customer.Customer.data.CustomerRequest;
import com.booksms.customer.Customer.data.CustomerResponse;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<CustomerResponse> getAll();
    List<CustomerResponse> getByCustomerPhone(int customerId);
    List<CustomerResponse> getByCustomerEmail(String customerEmail);
    Optional<CustomerResponse> getById(int customerId);

    Optional<CustomerResponse> register(CustomerRequest customerRequest);
}
