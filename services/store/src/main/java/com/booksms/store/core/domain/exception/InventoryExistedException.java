package com.booksms.store.core.domain.exception;

public class InventoryExisted extends RuntimeException{
    public InventoryExisted(String message) {
        super(message);
    }
}
