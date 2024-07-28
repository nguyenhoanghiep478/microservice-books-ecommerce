package com.booksms.customer.common.data.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    @NotNull(message = "email is required")
    @NotBlank(message= "email is required")
    @Email(message ="incorrect format for email")
    private String email;
    @NotNull(message = "phone is required")
    @NotBlank(message = "phone is required")
    private String phone;
    private String firstName;
    private String lastName;
    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    private String password;
}
