package com.example.test.models.repositories;

import com.example.test.models.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByName(String name);
}
