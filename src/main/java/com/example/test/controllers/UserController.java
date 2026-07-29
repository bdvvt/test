package com.example.test.controllers;

import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IAuthService;
import com.example.test.models.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final IAuthService authService;
    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @ModelAttribute UserReq req) {
        log.info("Received request to add new user: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New User Successfully")
                        .code(201)
                        .data(userService.createUser(req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @ModelAttribute  UserReq req){
        log.info("Updating user with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userService.updateUser(id,req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userService.findById(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        userService.deleteUser(id);
        log.info("Deleted user with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching all users");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userService.findAll())
                        .build()
        );
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<?> toggleBlockUser(@Valid @PathVariable Long id, BlockReq req) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(200)
                        .message("Cập nhật trạng thái khóa tài khoản thành công")
                        .data(authService.toggleBlockUser(id, req))
                        .build()
        );
    }
}
