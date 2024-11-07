package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.INTERNAL_ERROR;

public class CreateFailedException extends RuntimeException implements CustomException{
    public CreateFailedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INTERNAL_ERROR.getHttpStatus();
    }

    @Override
    public int getCode() {
        return INTERNAL_ERROR.getCode();
    }

    @Override
    public String getDescription() {
        return INTERNAL_ERROR.getDescription();
    }
}
