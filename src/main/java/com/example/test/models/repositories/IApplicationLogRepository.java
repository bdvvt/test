package com.example.test.models.repositories;
import com.example.test.models.entities.ApplicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {}
