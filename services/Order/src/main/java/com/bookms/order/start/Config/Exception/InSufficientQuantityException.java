package com.bookms.order.start.Config.Exception;

public class InSufficientQuantityException extends RuntimeException{
    public InSufficientQuantityException(String message){
        super(message);
    }
}
