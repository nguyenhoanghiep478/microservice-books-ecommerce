package com.booksms.store.core.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDTO {
    String errorDescription;
    int code;
    String error;
    Set<String> validationErrors;
}
