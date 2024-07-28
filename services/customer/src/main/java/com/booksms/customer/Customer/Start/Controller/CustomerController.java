package com.booksms.customer.Customer.Start.Controller;

import com.booksms.customer.common.service.ICustomerService;
import com.booksms.customer.common.data.data.dto.CustomerRequest;
import com.booksms.customer.common.data.data.dto.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }
    @GetMapping("/get-all-by-firstName/{firstName}")
    public ResponseEntity<List<CustomerResponse>> getAllCustomersByFirstName(@PathVariable String firstName) {
        List<CustomerResponse> results = customerService.getCustomersByFirstName(firstName);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable String email) {
        final CustomerResponse response = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable int id) {
        final CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-by-phone/{phone}")
    public ResponseEntity<CustomerResponse> getCustomerByPhone(@PathVariable String phone) {
        final CustomerResponse response = customerService.getByCustomerPhone(phone);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
       final CustomerResponse response =  this.customerService.register(customerRequest);
       return ResponseEntity.ok(response);
    }
    @PutMapping("/update")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
       final CustomerResponse response =  this.customerService.update(customerRequest);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable int id) {
        final CustomerResponse response = this.customerService.deleteById(id);
        return ResponseEntity.ok(response);
    }


}
