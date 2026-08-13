package com.example.test.models.repositories;
import com.example.test.models.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IPostRepository extends JpaRepository<Post, Long> {}
