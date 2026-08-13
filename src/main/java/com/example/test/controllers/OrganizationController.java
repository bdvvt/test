package com.example.test.controllers;

import com.example.test.models.dto.req.OrganizationReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IOrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final IOrganizationService organizationService;
    @PostMapping
    public ResponseEntity<?> addNewOrganization(@Valid @ModelAttribute OrganizationReq req) {
        log.info("Received request to add new organization: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Organization Successfully")
                        .code(201)
                        .data(organizationService.createOrganization(req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(@PathVariable Long id, @Valid @ModelAttribute  OrganizationReq req){
        log.info("Updating organization with ID: {}", id);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Organization Successfully")
                        .code(200)
                        .data(organizationService.updateOrganization(id,req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        log.info("Fetching organization with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Organization Successfully")
                        .code(200)
                        .data(organizationService.findById(id))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> dropout(@PathVariable Long id){
        organizationService.deleteOrganization(id);
        log.info("Deleted organization with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Organization Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching all organizations");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Organization Successfully")
                        .code(200)
                        .data(organizationService.findAll())
                        .build()
        );
    }
}
