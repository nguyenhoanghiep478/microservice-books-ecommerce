package com.booksms.distributor.core.domain.exception;

public class LicenseExistedException extends RuntimeException{
    public LicenseExistedException(String message){
        super(message);
    }
}
