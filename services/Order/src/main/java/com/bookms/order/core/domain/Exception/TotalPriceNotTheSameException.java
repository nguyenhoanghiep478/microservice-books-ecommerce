package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static com.bookms.order.core.domain.Exception.Error.TOTAL_PRICE_NOT_THE_SAME;

public class TotalPriceNotTheSameException extends RuntimeException implements CustomException{
    public TotalPriceNotTheSameException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return TOTAL_PRICE_NOT_THE_SAME.getHttpStatus();
    }

    @Override
    public int getCode() {
        return TOTAL_PRICE_NOT_THE_SAME.getCode();
    }

    @Override
    public String getDescription() {
        return TOTAL_PRICE_NOT_THE_SAME.getDescription();
    }
}
