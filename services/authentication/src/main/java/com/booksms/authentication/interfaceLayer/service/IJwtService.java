package com.booksms.authentication.interfaceLayer.service;

import com.booksms.authentication.core.entity.UserCredential;

public interface IJwtService {
    String generateToken(UserCredential userCredential,String[] permission);
    String isValidToken(String token);
    Boolean isExpiredToken(String token);
}
