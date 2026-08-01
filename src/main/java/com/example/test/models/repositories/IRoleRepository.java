package com.example.test.models.repositories;

import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
    boolean existsByRoleName(String roleName);
    List<Role> findAllByIdIn(Set<Long> id);
    Optional<Role> findByRoleName(String roleName);
}

