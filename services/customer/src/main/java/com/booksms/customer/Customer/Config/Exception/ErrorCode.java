package com.booksms.customer.Customer.Config.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NO_CODE(0,"No code",HttpStatus.NOT_IMPLEMENTED),
    USER_NOT_FOUND(303,"User Not Found",HttpStatus.NOT_FOUND),
    PHONE_NOT_FOUND(304,"Phone Not Found",HttpStatus.NOT_FOUND),
    PHONE_EXISTS(305,"Phone Exists",HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(306,"Email Exists",HttpStatus.BAD_REQUEST),
    USERNAME_EXISTS(307,"Username Exists",HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS(308,"Bad Credentials",HttpStatus.BAD_REQUEST),
    ;


    private final int code;
    private final String description;
    private final HttpStatus httpStatus;
    ErrorCode(final int code, final String description, final HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
