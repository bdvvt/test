package com.example.test.controllers;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.services.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @PostMapping
    public ResponseEntity<?> addNewRole(@Valid @ModelAttribute RoleReq req) {
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Role Successfully")
                        .code(201)
                        .data(roleService.createRole(req))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Role Successfully")
                        .code(200)
                        .data(roleService.findAll())
                        .build()
        );
    }
}
