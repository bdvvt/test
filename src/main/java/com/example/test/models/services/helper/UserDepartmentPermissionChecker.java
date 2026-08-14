package com.example.test.models.services.helper;

import com.example.test.models.repositories.IUserDepartmentRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import com.example.test.security.principal.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDepartmentPermissionChecker {
    private final IUserDepartmentRoleRepository repository;

    public boolean hasPermission(Authentication authentication, Long departmentId, String permission) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }
        return repository.existsByUserIdAndDepartmentIdAndRolePermissionsName(
                details.getUser().getId(), departmentId, permission);
    }

    public boolean canRead(Authentication authentication, Long departmentId) {
        return hasPermission(authentication, departmentId, "USER_DEPARTMENT_READ");
    }

    public boolean canCreate(Authentication authentication, Long departmentId) {
        return hasPermission(authentication, departmentId, "USER_DEPARTMENT_CREATE");
    }

    public boolean canUpdate(Authentication authentication, Long departmentId) {
        return hasPermission(authentication, departmentId, "USER_DEPARTMENT_UPDATE");
    }

    public boolean canDelete(Authentication authentication, Long departmentId) {
        return hasPermission(authentication, departmentId, "USER_DEPARTMENT_DELETE");
    }

    public void check(Long userId, Long departmentId, String permission) {
        boolean allowed = repository.existsByUserIdAndDepartmentIdAndRolePermissionsName(
                userId, departmentId, permission);
        if (!allowed) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "User không có quyền " + permission + " trên department " + departmentId);
        }
    }

}
