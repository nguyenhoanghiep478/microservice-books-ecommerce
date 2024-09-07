package com.booksms.book.core.domain.exception;

public class UpdateFailureException extends RuntimeException {
    public UpdateFailureException(String message) {
        super(message);
    }
}
