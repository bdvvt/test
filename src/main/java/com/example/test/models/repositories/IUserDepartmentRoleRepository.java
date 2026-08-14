package com.example.test.models.repositories;

import com.example.test.models.entities.UserDepartmentRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDepartmentRoleRepository extends JpaRepository<UserDepartmentRole, Long> {
    @Modifying
    @Query("""
            delete from UserDepartmentRole udr
            where udr.user.id = :userId
              and udr.department.id = :departmentId
            """)
    void deleteAllByUserIdAndDepartmentId(
            @Param("userId") Long userId,
            @Param("departmentId") Long departmentId
    );

    @Modifying
    @Query("""
            delete from UserDepartmentRole udr
            where udr.user.id = :userId
              and udr.department.id = :departmentId
              and udr.role.id = :roleId
            """)
    int deleteByUserIdAndDepartmentIdAndRoleId(
            @Param("userId") Long userId,
            @Param("departmentId") Long departmentId,
            @Param("roleId") Long roleId
    );

    @Query("""
            select case when count(udr) > 0 then true else false end
            from UserDepartmentRole udr
            join udr.role role
            join role.permissions permission
            join permission.departments department
            where udr.user.id = :userId
              and udr.department.id = :departmentId
              and permission.name = :permissionName
            """)
    boolean hasPermission(
            @Param("userId") Long userId,
            @Param("departmentId") Long departmentId,
            @Param("permissionName") String permissionName
    );
}
