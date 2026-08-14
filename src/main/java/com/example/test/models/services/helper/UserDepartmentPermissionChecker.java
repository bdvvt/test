package com.example.test.models.services.helper;

import com.example.test.models.repositories.IUserDepartmentRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDepartmentPermissionChecker {
    private final IUserDepartmentRoleRepository repository;

    public void check(Long userId, Long departmentId, String permission) {
        boolean allowed = repository.existsByUserIdAndDepartmentIdAndRolePermissionsName(
                userId, departmentId, permission);
        if (!allowed) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "User không có quyền " + permission + " trên department " + departmentId);
        }
    }

    public void canRead(Long userId, Long departmentId) {
        check(userId, departmentId, "USER_DEPARTMENT_READ");
    }

    public void canCreate(Long userId, Long departmentId) {
        check(userId, departmentId, "USER_DEPARTMENT_CREATE");
    }

    public void canUpdate(Long userId, Long departmentId) {
        check(userId, departmentId, "USER_DEPARTMENT_UPDATE");
    }

    public void canDelete(Long userId, Long departmentId) {
        check(userId, departmentId, "USER_DEPARTMENT_DELETE");
    }
}
