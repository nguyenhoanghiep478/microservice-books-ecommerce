package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.INSUFFICIENT_QUANTITY_EXCEPTION;

public class InSufficientQuantityException extends RuntimeException implements CustomException{
    public InSufficientQuantityException(String message){
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INSUFFICIENT_QUANTITY_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return INSUFFICIENT_QUANTITY_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return INSUFFICIENT_QUANTITY_EXCEPTION.getDescription();
    }
}
