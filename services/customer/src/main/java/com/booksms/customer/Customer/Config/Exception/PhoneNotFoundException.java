package com.booksms.customer.Customer.Config.Exception;

public class PhoneNotFoundException extends RuntimeException{
    public PhoneNotFoundException(String message){
        super(message);
    }
}
