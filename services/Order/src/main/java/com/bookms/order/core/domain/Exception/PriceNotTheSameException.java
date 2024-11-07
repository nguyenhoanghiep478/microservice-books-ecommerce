package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.PRICE_NOT_THE_SAME;

public class PriceNotTheSameException extends RuntimeException implements CustomException{
    public PriceNotTheSameException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return PRICE_NOT_THE_SAME.getHttpStatus();
    }

    @Override
    public int getCode() {
        return PRICE_NOT_THE_SAME.getCode();
    }

    @Override
    public String getDescription() {
        return PRICE_NOT_THE_SAME.getDescription();
    }
}
