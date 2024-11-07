package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.ORDER_EXISTED;

public class OrderExistException extends RuntimeException implements CustomException{
    public OrderExistException(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ORDER_EXISTED.getHttpStatus();
    }

    @Override
    public int getCode() {
        return ORDER_EXISTED.getCode();
    }

    @Override
    public String getDescription() {
        return ORDER_EXISTED.getDescription();
    }
}
