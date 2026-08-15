package com.example.test.models.repositories;
import com.example.test.models.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IApplicationLogRepository extends JpaRepository<Log, Long> {}
