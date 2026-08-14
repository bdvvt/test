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
    SELECT d
    FROM Department d
    JOIN d.managers m
    WHERE m.id = :memberId
""")
    List<Department> findManagedDepartmentsByMemberId(Long memberId);

;
}
