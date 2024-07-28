package com.booksms.customer.Customer.Config.ExceptionHandler;

import com.booksms.customer.Customer.Config.Exception.ExceptionResponse;
import com.booksms.customer.Customer.Config.Exception.PhoneExistException;
import com.booksms.customer.Customer.Config.Exception.PhoneNotFoundException;
import com.booksms.customer.Customer.Config.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static com.booksms.customer.Customer.Config.Exception.ErrorCode.*;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(UserNotFoundException ex) {
        return ResponseEntity
                .status(USER_NOT_FOUND.getHttpStatus())
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .code(USER_NOT_FOUND.getCode())
                                .errorDescription(USER_NOT_FOUND.getDescription())
                                .error(ex.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(PhoneNotFoundException ex) {
        return ResponseEntity
                .status(PHONE_NOT_FOUND.getHttpStatus())
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .code(PHONE_NOT_FOUND.getCode())
                                .errorDescription(PHONE_NOT_FOUND.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(PhoneExistException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(PhoneExistException ex) {
        return ResponseEntity
                .status(PHONE_EXISTS.getHttpStatus())
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .code(PHONE_EXISTS.getCode())
                                .errorDescription(PHONE_EXISTS.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(InternalError.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(InternalError ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .code(505)
                                .errorDescription("Please contact admin to have more information")
                                .build()
                );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidException(MethodArgumentNotValidException ex){
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST)
                .header("Content-Type","application/json")
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }
}
