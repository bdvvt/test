package com.example.test.controllers;

import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.user.IUserService;
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
public class UserController {
    private final IUserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<?> addNewUser(@AuthenticationPrincipal CustomUserDetails currentUser, @Valid @ModelAttribute UserReq req) {
        log.info("Received request to add new user: {}", req);
        Long adminOrgId = currentUser.getUser().getOrganization().getId();
        req.setOrganizationId(adminOrgId);
        log.info("Setting organizationId with user's organization id: {}", adminOrgId);

        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New User Successfully")
                        .code(201)
                        .data(userService.createUser(req))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id, @Valid @ModelAttribute  UserReq req){
        log.info("Updating user with ID: {}", id);
        Long orgId = currentUser.getUser().getOrganization().getId();
        req.setOrganizationId(orgId);
        return ResponseEntity.status(200).body(
                ApiResponse.builder()
                        .message("Updated User Successfully")
                        .code(200)
                        .data(userService.updateUser(id,req))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
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
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<?> dropout(@PathVariable Long id){
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


}
