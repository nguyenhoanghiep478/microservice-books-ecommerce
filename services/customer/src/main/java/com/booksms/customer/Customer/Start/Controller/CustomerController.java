package com.booksms.customer.Customer.Start.Controller;

import com.booksms.customer.Customer.common.service.ICustomerService;
import com.booksms.customer.Customer.data.CustomerRequest;
import com.booksms.customer.Customer.data.CustomerResponse;
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
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService
                .register(customerRequest).orElseThrow(
                        () -> new RuntimeException("register failed,Please try again!")
        ));
    }
}
