package com.booksms.distributor.core.domain.exception;

public class DistributorNotFoundException extends RuntimeException{
    public DistributorNotFoundException(String message){
        super(message);
    }
}
