package com.example.test.models.services.helper;

import com.example.test.security.principal.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserDepartmentPermissionChecker {
    public boolean hasPermission(Authentication authentication, Long departmentId, String permission) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        return details.getUser().getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .filter(permissionEntity -> permissionEntity.getDepartment() != null)
                .anyMatch(permissionEntity ->
                        departmentId.equals(permissionEntity.getDepartment().getId())
                        && permission.equals(permissionEntity.getName()));
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
}
