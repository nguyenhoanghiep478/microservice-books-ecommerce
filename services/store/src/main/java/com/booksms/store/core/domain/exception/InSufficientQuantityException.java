package com.booksms.store.core.domain.exception;

public class InSufficientQuantityException extends RuntimeException {
    public InSufficientQuantityException(String message) {
        super(message);
    }
}
