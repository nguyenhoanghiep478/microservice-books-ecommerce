package com.booksms.book.core.domain.exception;

public class MissingArgumentException extends RuntimeException{
    public MissingArgumentException(String message) {
        super(message);
    }
}
