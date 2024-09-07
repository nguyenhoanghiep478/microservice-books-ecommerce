package com.booksms.store.core.domain.exception;

public class InventoryExistedException extends RuntimeException{
    public InventoryExistedException(String message) {
        super(message);
    }
}
