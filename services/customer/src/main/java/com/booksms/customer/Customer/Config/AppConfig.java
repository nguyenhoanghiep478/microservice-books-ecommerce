package com.booksms.customer.Customer.Config;

import com.booksms.customer.Customer.common.service.GenericMapper;
import com.booksms.customer.Customer.data.Customer;
import com.booksms.customer.Customer.data.CustomerRequest;
import com.booksms.customer.Customer.data.CustomerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
