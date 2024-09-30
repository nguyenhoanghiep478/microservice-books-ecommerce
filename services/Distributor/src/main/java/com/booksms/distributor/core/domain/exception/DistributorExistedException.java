package com.booksms.distributor.core.domain.exception;

public class DistributorExistedException extends RuntimeException {
    public DistributorExistedException(String message) {
        super(message);
    }
}
