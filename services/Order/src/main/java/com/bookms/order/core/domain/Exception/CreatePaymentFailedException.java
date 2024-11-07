package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

public class CreatePaymentFailedException extends RuntimeException implements CustomException{
    public CreatePaymentFailedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public int getCode() {
        return 500;
    }

    @Override
    public String getDescription() {
        return "Create Payment Failed";
    }
}
