package com.booksms.gateway.ServiceGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthClient authClient;

    public void validateToken(String token) {
        if(!authClient.validateToken(token)){
            throw new RuntimeException("Invalid token");
        }
    }
}
