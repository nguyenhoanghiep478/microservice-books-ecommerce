package com.booksms.store.core.domain.exception;

public class InventoryNotExistedException extends RuntimeException {
    public InventoryNotExistedException(String message) {
        super(message);
    }
}
