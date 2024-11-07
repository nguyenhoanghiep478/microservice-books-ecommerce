package com.bookms.order.core.domain.Exception;

import org.springframework.http.HttpStatus;

import static com.bookms.order.core.domain.Exception.Error.BOOK_NOT_FOUND_EXCEPTION;

public class BookNotInStockException extends RuntimeException implements CustomException{
    public BookNotInStockException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return BOOK_NOT_FOUND_EXCEPTION.getHttpStatus();
    }

    @Override
    public int getCode() {
        return BOOK_NOT_FOUND_EXCEPTION.getCode();
    }

    @Override
    public String getDescription() {
        return BOOK_NOT_FOUND_EXCEPTION.getDescription();
    }
}
