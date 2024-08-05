package com.booksms.distributor.start.Config.Exception;

public class LicenseExistedException extends RuntimeException{
    public LicenseExistedException(String message){
        super(message);
    }
}
