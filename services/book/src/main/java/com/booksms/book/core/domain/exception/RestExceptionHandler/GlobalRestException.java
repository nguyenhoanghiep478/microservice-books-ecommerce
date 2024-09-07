package com.booksms.book.core.domain.exception.RestExceptionHandler;

import com.booksms.book.core.domain.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.Error;
import java.util.HashSet;
import java.util.Set;

import static com.booksms.book.core.domain.exception.Error.*;

@ControllerAdvice
public class GlobalRestException {
    private final String contentType= "application/json";
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionDTO> bookNotFoundException(BookNotFoundException e) {
        return ResponseEntity
                .status(BOOK_NOT_FOUND_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                       ExceptionDTO.builder()
                               .code(BOOK_NOT_FOUND_EXCEPTION.getCode())
                               .errorDescription(BOOK_NOT_FOUND_EXCEPTION.getDescription())
                               .error(e.getMessage())
                               .build()
                );

    }
    @ExceptionHandler(CategoryExistException.class)
    public ResponseEntity<ExceptionDTO> categoryExistException(CategoryExistException e) {
        return ResponseEntity
                .status(CATEGORY_EXISTED.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(CATEGORY_EXISTED.getCode())
                                .errorDescription(CATEGORY_EXISTED.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDTO> categoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity
                .status(CATEGORY_NOT_FOUND_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(CATEGORY_NOT_FOUND_EXCEPTION.getCode())
                                .errorDescription(CATEGORY_NOT_FOUND_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(InSufficientQuantityException.class)
    public ResponseEntity<ExceptionDTO> inSufficientQuantityException(InSufficientQuantityException e) {
        return ResponseEntity
                .status(INSUFFICIENT_QUANTITY_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(INSUFFICIENT_QUANTITY_EXCEPTION.getCode())
                                .errorDescription(INSUFFICIENT_QUANTITY_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
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
    @ExceptionHandler(CreateFailureException.class)
    public ResponseEntity<ExceptionDTO> createFailedException(CreateFailureException e) {
        return ResponseEntity
                .status(CREAT_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(CREAT_FAILURE_EXCEPTION.getCode())
                                .errorDescription(CREAT_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(UpdateFailureException.class)
    public ResponseEntity<ExceptionDTO> updateFailedException(UpdateFailureException e) {
        return ResponseEntity
                .status(UPDATE_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(UPDATE_FAILURE_EXCEPTION.getCode())
                                .errorDescription(UPDATE_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }
    @ExceptionHandler(MissingArgumentException.class)
    public ResponseEntity<ExceptionDTO> missingArgumentException(UpdateFailureException e) {
        return ResponseEntity
                .status(UPDATE_FAILURE_EXCEPTION.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(UPDATE_FAILURE_EXCEPTION.getCode())
                                .errorDescription(UPDATE_FAILURE_EXCEPTION.getDescription())
                                .error(e.getMessage())
                                .build()
                );

    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<ExceptionDTO> internalServerException(Error e) {
        return ResponseEntity
                .status(INTERNAL_ERROR.getHttpStatus())
                .header("Content-Type", contentType)
                .body(
                        ExceptionDTO.builder()
                                .code(CATEGORY_NOT_FOUND_EXCEPTION.getCode())
                                .errorDescription(CATEGORY_NOT_FOUND_EXCEPTION.getDescription())
                                .build()
                );

    }

}
