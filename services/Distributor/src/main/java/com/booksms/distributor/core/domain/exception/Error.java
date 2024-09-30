package com.booksms.distributor.core.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NO_CODE(0,"No code", HttpStatus.INTERNAL_SERVER_ERROR),
    DISTRIBUTOR_NOT_FOUND_EXCEPTION(400,"Distributor not found",HttpStatus.NOT_FOUND),
    DISTRIBUTOR_EXISTED_EXCEPTION(403,"Distributor already exists",HttpStatus.CONFLICT),
    LICENSE_NOT_FOUND_EXCEPTION(401,"License not found exception",HttpStatus.NOT_FOUND),
    LICENSE_EXISTED(402,"License existed",HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(500,"Invalid argument",HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR(500,"Internal error,please contact admin",HttpStatus.INTERNAL_SERVER_ERROR);
    ;


    final int code;
    final String description;
    final HttpStatus httpStatus;
    Error(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

}
