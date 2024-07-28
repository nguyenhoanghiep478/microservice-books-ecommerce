package com.booksms.customer.Customer.Config.Exception;

public class EmailExistException extends RuntimeException{
    public EmailExistException(String message){
        super(message);
    }
}
