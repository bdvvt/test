package com.example.test.controllers;

import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @ModelAttribute  UserReq req){
        log.info("Received request to update user: {}", req);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userService.updateUser(id,req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        log.info("Deleted user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN_SYSTEM')")
    public ResponseEntity<?> toggleBlockUser(@PathVariable Long id, @Valid @ModelAttribute BlockReq req) {
        log.info("Updating status for user ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(200)
                        .message("Cập nhật trạng thái khóa tài khoản thành công")
                        .data(userService.toggleBlockUser(id, req))
                        .build()
        );
    }



}
