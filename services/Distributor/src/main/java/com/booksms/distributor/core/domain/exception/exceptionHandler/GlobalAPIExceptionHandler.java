package com.booksms.distributor.core.domain.exception.exceptionHandler;

import com.booksms.distributor.core.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static com.booksms.distributor.core.domain.exception.Error.*;

@ControllerAdvice
public class GlobalAPIExceptionHandler {
    final String contentType = "application/json";


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Set<String> validationErrors = new HashSet<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            validationErrors.add(error.getDefaultMessage());
        });

        return ResponseEntity
                .status(INVALID_ARGUMENT.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(INVALID_ARGUMENT.getCode())
                                .errorDescription(INVALID_ARGUMENT.getDescription())
                                .validationErrors(validationErrors)
                                .build()
                )
                ;
    }
    @ExceptionHandler(DistributorNotFoundException.class)
    public ResponseEntity<ExceptionDTO> distributorNotFoundHandler(DistributorNotFoundException e) {
        return ResponseEntity
                .status(DISTRIBUTOR_NOT_FOUND_EXCEPTION.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(DISTRIBUTOR_NOT_FOUND_EXCEPTION.getCode())
                                .errorDescription(DISTRIBUTOR_NOT_FOUND_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(DistributorExistedException.class)
    public ResponseEntity<ExceptionDTO> distributorExitedHandler(DistributorExistedException e) {
        return ResponseEntity
                .status(DISTRIBUTOR_EXISTED_EXCEPTION.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(DISTRIBUTOR_EXISTED_EXCEPTION.getCode())
                                .errorDescription(DISTRIBUTOR_EXISTED_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(LicenseExistedException.class)
    public ResponseEntity<ExceptionDTO> distributorE(LicenseExistedException e) {
        return ResponseEntity
                .status(LICENSE_EXISTED.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(LICENSE_EXISTED.getCode())
                                .errorDescription(LICENSE_EXISTED.getDescription())
                                .error(e.getMessage())
                                .build()
                )
                ;
    }
    @ExceptionHandler(LicenseNotFoundException.class)
    public ResponseEntity<ExceptionDTO> LicenseNotFoundHandler(LicenseNotFoundException e) {
        return ResponseEntity
                .status(LICENSE_NOT_FOUND_EXCEPTION.getHttpStatus())
                .header("Content-Type",contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(LICENSE_NOT_FOUND_EXCEPTION.getCode())
                                .errorDescription(LICENSE_NOT_FOUND_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                )
                ;
    }
}
