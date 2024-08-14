package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.interfaceLayer.DTO.Request.AuthRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;

public interface IAuthService {
    UserDTO register(UserDTO credential);
    Boolean validateToken(String token);
    AuthResponse login(AuthRequest request);
}
