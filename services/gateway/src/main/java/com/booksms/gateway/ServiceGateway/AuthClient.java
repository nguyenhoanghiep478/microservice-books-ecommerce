package com.booksms.gateway.ServiceGateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service",url = "http://AUTHENTICATION-SERVICE//")
public interface AuthClient {

    @GetMapping("api/v1/auth/anonymous/validate-token")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}
