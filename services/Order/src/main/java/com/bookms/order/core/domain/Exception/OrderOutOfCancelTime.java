package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.ORDER_OUT_OF_CANCEL_TIME;

public class OrderOutOfCancelTime extends RuntimeException implements CustomException{
    public OrderOutOfCancelTime(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ORDER_OUT_OF_CANCEL_TIME.getHttpStatus();
    }

    @Override
    public int getCode() {
        return ORDER_OUT_OF_CANCEL_TIME.getCode();
    }

    @Override
    public String getDescription() {
        return ORDER_OUT_OF_CANCEL_TIME.getDescription();
    }
}
