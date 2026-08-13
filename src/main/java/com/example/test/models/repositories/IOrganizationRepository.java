package com.example.test.models.repositories;

import com.example.test.models.entities.Organization;
import com.example.test.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByName(String name);

}
