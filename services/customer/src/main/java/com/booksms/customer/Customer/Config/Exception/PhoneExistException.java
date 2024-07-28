package com.booksms.customer.Customer.Config.Exception;

public class PhoneExistException extends RuntimeException{
    public PhoneExistException(String message){
        super(message);
    }
}
