package com.booksms.customer.common.service;

import com.booksms.customer.common.data.data.dto.CustomerRequest;
import com.booksms.customer.common.data.data.dto.CustomerResponse;

import java.util.List;

public interface ICustomerService {
    List<CustomerResponse> getAll();
    CustomerResponse getByCustomerPhone(String phone);

    List<CustomerResponse> getCustomersByFirstName(String customerName);
    CustomerResponse register(CustomerRequest customerRequest);
    CustomerResponse update(CustomerRequest customerRequest);
    CustomerResponse deleteById(int id);
    CustomerResponse getCustomerById(int customerId);
    CustomerResponse getCustomerByEmail(String customerEmail);

}
