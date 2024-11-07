package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.ORDER_NOT_FOUND;

public class OrderNotFoundException extends RuntimeException implements CustomException{
    public OrderNotFoundException(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ORDER_NOT_FOUND.getHttpStatus();
    }

    @Override
    public int getCode() {
        return ORDER_NOT_FOUND.getCode();
    }

    @Override
    public String getDescription() {
        return ORDER_NOT_FOUND.getDescription();
    }
}
