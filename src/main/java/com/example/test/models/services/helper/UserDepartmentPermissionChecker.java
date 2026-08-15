package com.example.test.models.services.helper;

import com.example.test.models.repositories.IUserDepartmentRoleRepository;
import com.example.test.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDepartmentPermissionChecker {
    private final IUserDepartmentRoleRepository userDepartmentRoleRepository;

    public boolean hasPermission(Authentication authentication,Long departmentId,String permissionName
    ) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        return userDepartmentRoleRepository.hasPermission(
                details.getUser().getId(),
                departmentId,
                permissionName
        );
    }

    public boolean canRead(Authentication authentication, Long departmentId) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        Long userDepartmentId = details.getUser().getDepartment() == null
                ? null
                : details.getUser().getDepartment().getId();

        return departmentId.equals(userDepartmentId)
                || hasPermission(authentication, departmentId, "USER_DEPARTMENT_READ");
    }

    public boolean canCreate(Authentication authentication, Long departmentId) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        Long userDepartmentId = details.getUser().getDepartment() == null
                ? null
                : details.getUser().getDepartment().getId();

        return departmentId.equals(userDepartmentId)
                || hasPermission(authentication, departmentId, "USER_DEPARTMENT_CREATE");
    }

    public boolean canUpdate(Authentication authentication, Long departmentId) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        Long userDepartmentId = details.getUser().getDepartment() == null
                ? null
                : details.getUser().getDepartment().getId();

        return departmentId.equals(userDepartmentId)
                || hasPermission(authentication, departmentId, "USER_DEPARTMENT_UPDATE");
    }

    public boolean canDelete(Authentication authentication, Long departmentId) {
        if (authentication == null
                || !(authentication.getPrincipal() instanceof CustomUserDetails details)) {
            return false;
        }

        Long userDepartmentId = details.getUser().getDepartment() == null
                ? null
                : details.getUser().getDepartment().getId();

        return departmentId.equals(userDepartmentId)
                || hasPermission(authentication, departmentId, "USER_DEPARTMENT_DELETE");
    }
}
