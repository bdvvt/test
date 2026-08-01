package com.example.test.controllers;

import com.example.test.models.dto.req.UserOrganizationReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IUserOrganizationService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserOrganizationController {
    private final IUserOrganizationService userOrganizationService;

    @PostMapping("/organizations")
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<?> addNewUser(@AuthenticationPrincipal CustomUserDetails currentUser, @Valid @ModelAttribute UserOrganizationReq req) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Setting organizationId with user's organization id: {}", orgId);

        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New User Successfully")
                        .code(201)
                        .data(userOrganizationService.createUserInOrganization(orgId, req))
                        .build()
        );
    }

    @PutMapping("/{id}/organizations")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long id, @Valid @ModelAttribute  UserOrganizationReq req){
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Updating user ID: {} in org: {}", id, orgId);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userOrganizationService.updateUserInOrganization(id,orgId,req))
                        .build()
        );
    }

    @GetMapping("/{id}/organizations")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<?> findById(@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Fetching user with ID: {} in org: {}", id, orgId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userOrganizationService.findByIdInOrganization(id, orgId))
                        .build()
        );
    }

    @DeleteMapping("/{id}/organizations")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<?> dropout(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long id){
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Deleted user with ID: {} in org: {}", id, orgId);
        userOrganizationService.deleteUserInOrganization(id, orgId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping("/organizations")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Fetching all users in org: {}", orgId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userOrganizationService.listUsersInOrganization(orgId))
                        .build()
        );
    }

}
