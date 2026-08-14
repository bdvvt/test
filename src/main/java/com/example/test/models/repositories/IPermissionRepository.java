package com.example.test.models.repositories;

import com.example.test.models.entities.Permission;
import com.example.test.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByName(String name);
    List<Permission> findAllByIdIn(Set<Long> id);

}
