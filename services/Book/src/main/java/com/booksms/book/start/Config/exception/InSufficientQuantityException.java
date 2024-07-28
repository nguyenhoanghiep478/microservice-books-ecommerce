package com.booksms.book.start.Config.exception;

public class InSufficientQuantityException extends RuntimeException{
    public InSufficientQuantityException(String message){
        super(message);
    }
}

