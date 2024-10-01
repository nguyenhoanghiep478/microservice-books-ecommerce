package com.booksms.store.core.domain.exception.RestExceptionHandler;

import com.booksms.store.core.domain.exception.BookException.BookNotFoundException;
import com.booksms.store.core.domain.exception.CategoryExistException;
import com.booksms.store.core.domain.exception.CategoryNotFoundException;
import com.booksms.store.core.domain.exception.CreateFailureException;
import com.booksms.store.core.domain.exception.Error;
import com.booksms.store.core.domain.exception.ExceptionDTO;
import com.booksms.store.core.domain.exception.InSufficientQuantityException;
import com.booksms.store.core.domain.exception.MissingArgumentException;
import com.booksms.store.core.domain.exception.UpdateFailureException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestException {
    private final String contentType = "application/json";

    public GlobalRestException() {
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<ExceptionDTO> bookNotFoundException(BookNotFoundException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.BOOK_NOT_FOUND_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.BOOK_NOT_FOUND_EXCEPTION.getCode()).errorDescription(Error.BOOK_NOT_FOUND_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({CategoryExistException.class})
    public ResponseEntity<ExceptionDTO> categoryExistException(CategoryExistException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.CATEGORY_EXISTED.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.CATEGORY_EXISTED.getCode()).errorDescription(Error.CATEGORY_EXISTED.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<ExceptionDTO> categoryNotFoundException(CategoryNotFoundException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.CATEGORY_NOT_FOUND_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.CATEGORY_NOT_FOUND_EXCEPTION.getCode()).errorDescription(Error.CATEGORY_NOT_FOUND_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({InSufficientQuantityException.class})
    public ResponseEntity<ExceptionDTO> inSufficientQuantityException(InSufficientQuantityException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.INSUFFICIENT_QUANTITY_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.INSUFFICIENT_QUANTITY_EXCEPTION.getCode()).errorDescription(Error.INSUFFICIENT_QUANTITY_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Set<String> validationErrors = new HashSet();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            validationErrors.add(error.getDefaultMessage());
        });
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.INVALID_ARGUMENT.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.INVALID_ARGUMENT.getCode()).errorDescription(Error.INVALID_ARGUMENT.getDescription()).validationErrors(validationErrors).build());
    }

    @ExceptionHandler({CreateFailureException.class})
    public ResponseEntity<ExceptionDTO> createFailedException(CreateFailureException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.CREAT_FAILURE_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.CREAT_FAILURE_EXCEPTION.getCode()).errorDescription(Error.CREAT_FAILURE_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({UpdateFailureException.class})
    public ResponseEntity<ExceptionDTO> updateFailedException(UpdateFailureException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.UPDATE_FAILURE_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.UPDATE_FAILURE_EXCEPTION.getCode()).errorDescription(Error.UPDATE_FAILURE_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({MissingArgumentException.class})
    public ResponseEntity<ExceptionDTO> missingArgumentException(UpdateFailureException e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.UPDATE_FAILURE_EXCEPTION.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.UPDATE_FAILURE_EXCEPTION.getCode()).errorDescription(Error.UPDATE_FAILURE_EXCEPTION.getDescription()).error(e.getMessage()).build());
    }

    @ExceptionHandler({java.lang.Error.class})
    public ResponseEntity<ExceptionDTO> internalServerException(java.lang.Error e) {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.status(Error.INTERNAL_ERROR.getHttpStatus()).header("Content-Type", new String[]{"application/json"})).body(ExceptionDTO.builder().code(Error.CATEGORY_NOT_FOUND_EXCEPTION.getCode()).errorDescription(Error.CATEGORY_NOT_FOUND_EXCEPTION.getDescription()).build());
    }
}
