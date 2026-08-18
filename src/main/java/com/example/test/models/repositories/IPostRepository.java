package com.example.test.models.repositories;
import com.example.test.models.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrganizationIdAndUserDepartmentId(Long orgId, Long deptId);
}
