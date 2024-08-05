package com.booksms.book.core.domain.exception;

public class InSufficientQuantityException extends RuntimeException{
    public InSufficientQuantityException(String message){
        super(message);
    }
}

