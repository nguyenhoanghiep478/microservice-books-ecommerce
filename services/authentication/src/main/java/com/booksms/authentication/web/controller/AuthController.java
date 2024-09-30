package com.booksms.authentication.web.controller;

import com.booksms.authentication.interfaceLayer.DTO.Request.UpdateUserRequest;
import com.booksms.authentication.interfaceLayer.DTO.Request.UserDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.ResponseDTO;
import com.booksms.authentication.interfaceLayer.DTO.Response.UserResponseDTO;
import com.booksms.authentication.interfaceLayer.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PutMapping("/update-user")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UpdateUserRequest request) {
        UserResponseDTO response = authService.updateUser(request);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("update successful"))
                .result(response)
                .build());
    }
    @DeleteMapping("/delete/{id}/{state}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("id") Integer id, @PathVariable("state") Boolean state) {
        authService.deleteUserById(id,state);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("block user successful"))
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

    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        List<UserResponseDTO> response = authService.getAll();
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get all user successful"))
                .result(response)
                .build());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        UserResponseDTO response = authService.getById(id);
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(200)
                .message(Collections.singletonList("get user by id successful"))
                .result(response)
                .build());
    }


    @GetMapping("/validate-token")
    public Boolean validateToken(@RequestHeader("Authorization") String token) {
        return authService.validateToken(token);
    }
}
