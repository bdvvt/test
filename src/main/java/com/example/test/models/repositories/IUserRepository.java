package com.example.test.models.repositories;

import com.example.test.models.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndOrganizationId(Long id, Long orgId);
    List<User> findAllByOrganizationId(Long orgId);
    void deleteByEnabledFalseAndOtpExpirationBefore(LocalDateTime cutoffTime);
    Optional<User> findByIdAndOrganizationIdAndDepartmentId(Long id, Long orgId, Long deptId);
    List<User> findAllByOrganizationIdAndDepartmentId(Long orgId, Long deptId);

    @EntityGraph(attributePaths = {
            "roles",
            "roles.permissions",
            "roles.permissions.departments"
    })
    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.username = :identifier")
    Optional<User> findByEmailOrUsername(@Param("identifier") String identifier);

}
