package com.example.test.controllers;

import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.permission.IPermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final IPermissionService permissionService;
    @PostMapping
    public ResponseEntity<?> addNewPermission(@Valid @ModelAttribute PermissionReq req) {
        log.info("Received request to add new permission: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Permission Successfully")
                        .code(201)
                        .data(permissionService.createPermission(req))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching list of all permissions");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Permission Successfully")
                        .code(200)
                        .data(permissionService.findAll())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        permissionService.deletePermission(id);
        log.info("Deleting permission with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Permission Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @ModelAttribute PermissionReq req){
        log.info("Updating permission with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Role Successfully")
                        .code(200)
                        .data(permissionService.updatePermission(id,req))
                        .build()
        );
    }
}
