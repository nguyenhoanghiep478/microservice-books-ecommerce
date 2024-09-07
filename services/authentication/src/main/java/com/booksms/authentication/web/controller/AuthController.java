package com.booksms.authentication.web.controller;

import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.AuthResponse;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final IAuthService authService;

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserDTO response = authService.findById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get user successful"))
                .result(response)
                .build());
    }

    @GetMapping("/total-user")
    public ResponseEntity<?> totalUser() {
        Integer total = authService.getTotalUser();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("login successful"))
                .result(total)
                .build());
    }


    @GetMapping("/validate-token")
    public Boolean validateToken(@RequestHeader("Authorization") String token) {
        return authService.validateToken(token);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token,@CookieValue(name = "refresh-token",defaultValue = "") String refreshToken) throws AuthenticationException {
        AuthResponse response = authService.refershToken(token,refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,response.getRefreshToken().getValue())
                .body(ResponseDTO.builder()
                        .status(200)
                        .message(Collections.singletonList("login successful"))
                        .result(response)
                        .build())
                ;
    }
}
