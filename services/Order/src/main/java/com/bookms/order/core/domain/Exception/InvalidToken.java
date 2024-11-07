package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.INVALID_TOKEN;

public class InvalidToken extends RuntimeException implements CustomException{
    public InvalidToken(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return INVALID_TOKEN.getHttpStatus();
    }

    @Override
    public int getCode() {
        return INVALID_TOKEN.getCode();
    }

    @Override
    public String getDescription() {
        return INVALID_TOKEN.getDescription();
    }
}
