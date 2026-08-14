package com.example.test.controllers;

import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IDepartmentService;
import com.example.test.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_CREATOR')")
    public ResponseEntity<?> addNewDepartment(@AuthenticationPrincipal CustomUserDetails currentUser, @Valid @ModelAttribute DepartmentReq req) {
        if (currentUser.getUser().getOrganization() == null) {
            throw new RuntimeException("Tài khoản của bạn chưa thuộc về công ty/tổ chức nào!");
        }
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Received request to add new department: {}", req);
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Department Successfully")
                        .code(201)
                        .data(departmentService.createDepartment(orgId,req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_CREATOR')")
    public ResponseEntity<?> updateDepartment(@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id, @Valid @ModelAttribute DepartmentReq req){
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Updating department ID: {} in org: {}", id, orgId);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated Department Successfully")
                        .code(200)
                        .data(departmentService.updateDepartment(orgId, id, req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long id) {
        if (currentUser.getUser() == null || currentUser.getUser().getOrganization() == null) {
            throw new RuntimeException("Tài khoản của bạn chưa thuộc về công ty/tổ chức nào!");
        }
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Fetching department with ID: {}", id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Department Successfully")
                        .code(200)
                        .data(departmentService.findById(id, orgId))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_CREATOR')")
    public ResponseEntity<?> dropout(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long id){
        Long orgId = currentUser.getUser().getOrganization().getId();
        departmentService.deleteDepartment(id, orgId);
        log.info("Deleted department with ID: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted Department Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser) {
        if (currentUser.getUser() == null || currentUser.getUser().getOrganization() == null) {
            throw new RuntimeException("Tài khoản của bạn chưa thuộc về công ty/tổ chức nào!");
        }
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Fetching all departments");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Department Successfully")
                        .code(200)
                        .data(departmentService.findAll(orgId))
                        .build()
        );
    }

}
