package com.example.test.controllers;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @PostMapping
    public ResponseEntity<?> addNewRole(@Valid @ModelAttribute RoleReq req) {
        log.info("Received request to add new role: {}", req);
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
        log.info("Fetching list of all roles");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Role Successfully")
                        .code(200)
                        .data(roleService.findAll())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        roleService.deleteRole(id);
        log.info("Deleting role with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Role Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @ModelAttribute  RoleReq req){
        log.info("Updating role with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Role Successfully")
                        .code(200)
                        .data(roleService.updateRole(id,req))
                        .build()
        );
    }
}
