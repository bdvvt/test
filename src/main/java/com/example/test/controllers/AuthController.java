package com.example.test.controllers;

import com.example.test.models.dto.req.LoginReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IAuthService;
import com.example.test.models.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq req) {
        authService.register(req);
        log.info("User registered successfully: {}", req.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Register User Successfully")
                        .code(201)
                        .data("Register successfully")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req) {
        log.info("Attempting login for user: {}", req.getUsername());
        return ResponseEntity.ok()
                .body(
                        ApiResponse.builder()
                                .message("Login User Successfully")
                                .code(200)
                                .data(authService.login(req))
                                .build()
                );
    }
}


