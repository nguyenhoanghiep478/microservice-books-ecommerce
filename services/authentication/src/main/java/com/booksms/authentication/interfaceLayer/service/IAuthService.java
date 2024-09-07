package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.interfaceLayer.DTO.Request.AuthRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.CreateResetPasswordRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.ResetPasswordRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;

import javax.security.sasl.AuthenticationException;

public interface IAuthService {
    UserDTO register(UserDTO credential);
    Boolean validateToken(String token);
    AuthResponse login(AuthRequest request);

    Integer getTotalUser();

    UserDTO findById(Integer id);

    AuthResponse refershToken(String token, String refreshToken) throws AuthenticationException;

    void createResetPasswordRequest(CreateResetPasswordRequest request);

    void updatePassword(ResetPasswordRequest request);
}
