package com.booksms.customer.Customer.common.service.impl;

import com.booksms.customer.Customer.common.repository.ICustomerRepository;
import com.booksms.customer.Customer.common.service.GenericMapper;
import com.booksms.customer.Customer.common.service.ICustomerService;
import com.booksms.customer.Customer.data.Customer;
import com.booksms.customer.Customer.data.CustomerRequest;
import com.booksms.customer.Customer.data.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService implements ICustomerService {
    private final ICustomerRepository repository;
    private final GenericMapper<Customer,CustomerResponse,CustomerRequest> mapper;
    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = repository.findAll();
        if(customers.isEmpty()){
           throw new InternalException("We trying to update,please comeback later");
        }
        return mapper.toListResponse(customers,CustomerResponse.class);

    }

    @Override
    public List<CustomerResponse> getByCustomerPhone(int customerId) {
        return List.of();
    }

    @Override
    public List<CustomerResponse> getByCustomerEmail(String customerEmail) {
        return List.of();
    }

    @Override
    public Optional<CustomerResponse> getById(int customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<CustomerResponse> register(CustomerRequest customerRequest) {
        var entity = mapper.toEntity(customerRequest,Customer.class);
        return Optional.of(mapper.toResponse(repository.save(entity),CustomerResponse.class));
    }


}
