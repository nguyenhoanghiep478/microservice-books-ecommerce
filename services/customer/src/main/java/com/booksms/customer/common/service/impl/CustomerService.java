package com.booksms.customer.common.service.impl;

import com.booksms.customer.Customer.Config.Exception.EmailExistException;
import com.booksms.customer.Customer.Config.Exception.PhoneExistException;
import com.booksms.customer.Customer.Config.Exception.UserNotFoundException;
import com.booksms.customer.common.data.data.repository.ICustomerRepository;
import com.booksms.customer.common.service.GenericMapper;
import com.booksms.customer.common.service.ICustomerService;
import com.booksms.customer.common.data.data.Entity.Customer;
import com.booksms.customer.common.data.data.dto.CustomerRequest;
import com.booksms.customer.common.data.data.dto.CustomerResponse;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public CustomerResponse getByCustomerPhone(String phone) {
        if(!isValidPhone(phone)){
            throw new BadRequestException("Phone number is not valid");
        }
        var customer = repository.findOneByPhone(phone).orElseThrow(
                () -> new UserNotFoundException(String.format("User with phone number %s not found", phone))
        );
        return mapper.toResponse(customer,CustomerResponse.class);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String customerEmail) {
        var customer = repository.findCustomerByEmail(customerEmail).orElseThrow(
                () -> new UserNotFoundException("Customer with email " + customerEmail + " not found")
        );
        return mapper.toResponse(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getCustomersByFirstName(String firstName) {
        List<Customer> customers = repository.findCustomersByFirstName(firstName);
        return mapper.toListResponse(customers,CustomerResponse.class);
    }

    @Override
    public CustomerResponse getCustomerById(int customerId) {
        var customer = repository.findById(customerId).orElseThrow(
                () -> new UserNotFoundException("Customer with id " + customerId + " not found")
        );
        return mapper.toResponse(customer, CustomerResponse.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse register(CustomerRequest customerRequest) {
        var customer = repository.findCustomerByEmail(customerRequest.getEmail());
        if(customer.isPresent()){
            throw new EmailExistException( String.format("Customer with email %s already exist", customerRequest.getEmail()));
        }
        if(isValidPhone(customerRequest.getPhone())){
            throw new BadRequestException(String.format("Phone number %s is not valid", customerRequest.getPhone()));
        }
        customer = repository.findOneByPhone(customerRequest.getPhone());
        if(customer.isPresent()){
            throw new PhoneExistException(String.format("Phone number %s already exist", customerRequest.getPhone()));
        }
        var entity = mapper.toEntity(customerRequest,Customer.class);
        if(entity == null){
            throw new InternalException("Registration failed, please try again");
        }
        return mapper.toResponse(repository.save(entity),CustomerResponse.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse update(CustomerRequest customerRequest) {
        var customer = repository.findCustomerByEmail(customerRequest.getEmail());
        if(customer.isEmpty()){
            throw new UserNotFoundException("User not found with email " + customerRequest.getEmail());
        }
        if(!isValidPhone(customerRequest.getPhone())){
            throw new BadRequestException("Phone number is not valid");
        }
        customer = repository.findOneByPhone(customerRequest.getPhone());
        if(customer.isEmpty()){
            throw new UserNotFoundException("User not found with phone number "+ customerRequest.getPhone());
        }
        var mergedEntity = mapper.toEntity(customerRequest,Customer.class);
        var result =  repository.save(mergedEntity);
        if(result == null){
            throw new InternalException("something went wrong, cannot update customer,please try again");
        }
        return mapper.toResponse(result,CustomerResponse.class);

    }

    @Override
    public CustomerResponse deleteById(int id) {
        var customer = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Customer with id " + id + " not found")
        );
        repository.delete(customer);
        return mapper.toResponse(customer,CustomerResponse.class);
    }

    boolean isValidPhone(String phone) {
        String regex = "^0\\d{9}";
        return phone.matches(regex);
    }

}
