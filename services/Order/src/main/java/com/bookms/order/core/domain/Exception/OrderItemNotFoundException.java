package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.ORDER_ITEM_NOT_FOUND;

public class OrderItemNotFoundException extends RuntimeException implements CustomException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return ORDER_ITEM_NOT_FOUND.getHttpStatus();
    }

    @Override
    public int getCode() {
        return ORDER_ITEM_NOT_FOUND.getCode();
    }

    @Override
    public String getDescription() {
        return ORDER_ITEM_NOT_FOUND.getDescription();
    }
}
