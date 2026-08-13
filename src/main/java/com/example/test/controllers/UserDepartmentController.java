package com.example.test.controllers;

import com.example.test.models.dto.req.UserOrganizationReq;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IUserDepartmentService;
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
public class UserDepartmnetController {
    private final IUserDepartmentService userDepartmentService;

    @GetMapping("/{id}/department/organization")
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseEntity<?> findById(@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        Long deptId = currentUser.getUser().getDepartment().getId();
        log.info("Fetching user with ID: {} in dept: {}", id, deptId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userDepartmentService.findByUserInDepartment(id, orgId, deptId))
                        .build()
        );
    }

    @DeleteMapping("/{id}/department/organization")
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public ResponseEntity<?> dropout(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long id){
        Long orgId = currentUser.getUser().getOrganization().getId();
        Long deptId = currentUser.getUser().getDepartment().getId();
        log.info("Deleted user with ID: {} in dept: {}", id, deptId);
        userDepartmentService.deleteUserInDepartment(id, orgId, deptId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                ApiResponse.builder()
                        .message("Deleted User Successfully")
                        .code(204)
                        .data(null)
                        .build()
        );
    }

    @GetMapping("/department/organization")
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        Long deptId = currentUser.getUser().getDepartment().getId();
        log.info("Fetching all users in dept: {}", deptId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userDepartmentService.listUsersInDepartment(orgId, deptId))
                        .build()
        );
    }

    @GetMapping("/department/{deptId}/organization")
    @PreAuthorize("""
                        principal.user.department.code == 'HR' || 
                        principal.user.department.code == 'IT'
                    """)
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long deptId) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        log.info("Fetching all users in dept: {}", deptId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get User Successfully")
                        .code(200)
                        .data(userDepartmentService.listUsersInDepartment(orgId, deptId))
                        .build()
        );
    }

}
