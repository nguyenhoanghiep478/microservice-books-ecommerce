package com.booksms.authentication.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotNull(message = "email is required for reset password")
    private String email;
    @NotNull(message = "id is required for reset password")
    private Integer id;

    private String password;

}
