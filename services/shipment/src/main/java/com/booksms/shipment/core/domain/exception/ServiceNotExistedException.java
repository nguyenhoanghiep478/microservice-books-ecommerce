package com.booksms.shipment.core.domain.exception;

public class ServiceNotExistedException extends RuntimeException{
    public ServiceNotExistedException(String message) {
        super(message);
    }
}
