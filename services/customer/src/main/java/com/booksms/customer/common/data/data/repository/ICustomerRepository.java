package com.booksms.customer.common.data.data.repository;

import com.booksms.customer.common.data.data.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends MongoRepository<Customer, Integer> {
    Optional<Customer> findOneByPhone(String phone);
    Optional<Customer> findCustomerByEmail(String email);
    List<Customer> findCustomersByFirstName(String firstName);
}
