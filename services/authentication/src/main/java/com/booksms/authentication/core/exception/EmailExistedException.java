package com.booksms.authentication.core.exception;

import org.springframework.http.HttpStatus;

import static com.booksms.authentication.core.exception.Error.EMAIL_EXISTED;

public class EmailExistedException extends RuntimeException implements CustomException{
    public EmailExistedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return EMAIL_EXISTED.getHttpStatus();
    }

    @Override
    public int getCode() {
        return EMAIL_EXISTED.getCode();
    }

    @Override
    public String getDescription() {
        return EMAIL_EXISTED.getDescription();
    }
}
