package com.example.test.models.repositories;

import com.example.test.models.entities.UserDepartmentRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDepartmentRoleRepository extends JpaRepository<UserDepartmentRole, Long> {
    boolean existsByUserIdAndDepartmentIdAndRolePermissionsName(Long userId, Long departmentId, String permissionName);
}
