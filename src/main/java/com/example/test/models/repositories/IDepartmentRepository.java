package com.example.test.models.repositories;

import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentName(String departmentName);
    Optional<Department> findByIdAndOrganizationId(Long id, Long orgId);
    List<Department> findAllByOrganizationId(Long orgId);

    @Query("""
        SELECT m FROM Department d 
        JOIN d.managers m 
        WHERE d.id = :deptId AND d.organization.id = :orgId
    """)
    List<User> findManagersByDepartmentAndOrg(@Param("deptId") Long deptId, @Param("orgId") Long orgId);

    @Query("""
    SELECT d
    FROM Department d
    JOIN d.managers m
    WHERE m.id = :memberId
""")
    List<Department> findManagedDepartmentsByMemberId(Long memberId);


    @Query("""
        SELECT d.id 
        FROM Department d 
        JOIN d.managers m 
        WHERE m.id = :memberId
    """)
    List<Long> findDepartmentIdsByMemberId(@Param("memberId") Long memberId);


    @Query("""
        SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END 
        FROM Department d 
        JOIN d.users u 
        WHERE u.id = :userId AND d.id = :departmentId
    """)
    boolean existsByUserUserIdAndDepartmentId(@Param("userId") Long userId, @Param("departmentId") Long departmentId);
}
