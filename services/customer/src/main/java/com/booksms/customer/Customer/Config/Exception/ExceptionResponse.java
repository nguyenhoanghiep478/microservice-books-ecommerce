package com.booksms.customer.Customer.Config.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    int code;
    String error;
    String errorDescription;
    private Set<String> validationErrors;

}
