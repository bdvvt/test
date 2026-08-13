package com.example.test.controllers;

import com.example.test.models.dto.req.ActiveUserReq;
import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.LoginReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.auth.IAuthService;
import com.example.test.models.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/block")
    public ResponseEntity<?> toggleBlockUser(@Valid @PathVariable Long id, @RequestParam("status") boolean status) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(200)
                        .message("Cập nhật trạng thái khóa tài khoản thành công")
                        .data(authService.toggleBlockUser(id, status))
                        .build()
        );
    }

    @PostMapping("/active-user")
    public ResponseEntity<?> activeUser(@Valid @RequestBody ActiveUserReq req) {
        log.info("Attempting activation for email: {}", req.getEmail());
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Active User Successfully")
                        .code(200)
                        .data(authService.activeUser(req))
                        .build()
        );
    }
}


