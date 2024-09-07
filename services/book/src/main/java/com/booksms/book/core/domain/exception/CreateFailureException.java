package com.booksms.book.core.domain.exception;

public class CreateFailureException extends RuntimeException{
    public CreateFailureException(String message) {
        super(message);
    }
}
