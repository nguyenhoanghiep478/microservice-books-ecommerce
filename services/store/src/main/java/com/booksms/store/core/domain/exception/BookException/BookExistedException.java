package com.booksms.store.core.domain.exception.BookException;

public class BookExistedException extends RuntimeException {
    public BookExistedException(final String message) {
        super(message);
    }
}
