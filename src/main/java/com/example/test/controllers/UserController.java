package com.example.test.controllers;

import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping
    public ResponseEntity<?> addNewUser(@Valid @ModelAttribute UserReq req) {
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userService.findAll())
                        .build()
        );
    }
}
