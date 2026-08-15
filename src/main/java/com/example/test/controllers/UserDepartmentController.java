package com.example.test.controllers;

import com.example.test.models.dto.req.UpdateRoleUser;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IUserDepartmentService;
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
public class UserDepartmentController {
    private final IUserDepartmentService userDepartmentService;

    @GetMapping("/{id}/department/{deptId}")
    @PreAuthorize("@userDepartmentPermissionChecker.canRead(authentication, #deptId)")
    public ResponseEntity<?> findById(@AuthenticationPrincipal CustomUserDetails currentUser,
                                      @PathVariable Long id,
                                      @PathVariable Long deptId) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        return ResponseEntity.ok(ApiResponse.builder().message("Get User Successfully").code(200)
                .data(userDepartmentService.findByUserInDepartment(id, orgId, deptId)).build());
    }

    @DeleteMapping("/{id}/department/{deptId}")
    @PreAuthorize("@userDepartmentPermissionChecker.canDelete(authentication, #deptId)")
    public ResponseEntity<?> dropout(@AuthenticationPrincipal CustomUserDetails currentUser,
                                     @PathVariable Long id,
                                     @PathVariable Long deptId) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        userDepartmentService.deleteUserInDepartment(id, orgId, deptId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.builder()
                .message("Deleted User Successfully").code(204).data(null).build());
    }

    @DeleteMapping("/{id}/department/{deptId}/roles/{roleId}")
    @PreAuthorize("@userDepartmentPermissionChecker.canUpdate(authentication, #deptId)")
    public ResponseEntity<?> revokeUserRole(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PathVariable Long id,
            @PathVariable Long deptId,
            @PathVariable Long roleId) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        userDepartmentService.revokeUserRoleInDept(id, orgId, deptId, roleId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Revoked User Department Role Successfully")
                .code(200)
                .data(null)
                .build());
    }

    @GetMapping("/department/{deptId}")
    @PreAuthorize("@userDepartmentPermissionChecker.canRead(authentication, #deptId)")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser,
                                     @PathVariable Long deptId) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        return ResponseEntity.ok(ApiResponse.builder().message("Get User Successfully").code(200)
                .data(userDepartmentService.listUsersInDepartment(orgId, deptId)).build());
    }

    @PutMapping("/{id}/department/{deptId}/roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN_SYSTEM','ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomUserDetails currentUser,
                                        @PathVariable Long id,
                                        @PathVariable Long deptId,
                                        @Valid @ModelAttribute UpdateRoleUser req) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        return ResponseEntity.ok(ApiResponse.builder().message("Updated User Successfully").code(200)
                .data(userDepartmentService.updateUserRoleInDept(id, orgId, deptId, req)).build());
    }
}
