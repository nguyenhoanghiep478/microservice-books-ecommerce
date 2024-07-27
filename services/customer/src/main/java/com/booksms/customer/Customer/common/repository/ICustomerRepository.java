package com.booksms.customer.Customer.common.repository;

import com.booksms.customer.Customer.data.Customer;
import com.booksms.customer.Customer.data.CustomerResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends MongoRepository<Customer, Integer> {
    Customer findOneByPhone(String phone);
}
