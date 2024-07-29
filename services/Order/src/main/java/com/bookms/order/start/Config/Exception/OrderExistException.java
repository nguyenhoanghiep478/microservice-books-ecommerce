package com.bookms.order.start.Config.Exception;

public class OrderExistException extends RuntimeException{
    public OrderExistException(String message){
        super(message);
    }
}
