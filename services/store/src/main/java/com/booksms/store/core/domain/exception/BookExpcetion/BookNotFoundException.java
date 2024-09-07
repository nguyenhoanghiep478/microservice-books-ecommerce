package com.booksms.store.core.domain.exception.BookExpcetion;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
