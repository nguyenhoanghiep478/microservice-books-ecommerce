package com.booksms.shipment.core.domain.exception;

public class CreateFailureException extends RuntimeException {
    public CreateFailureException(String message) {
        super(message);
    }
}
