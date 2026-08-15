package com.example.test.models.services;

import com.example.test.models.entities.Log;
import com.example.test.models.entities.User;

import java.util.List;

public interface ILogService {
    void recordAudit(String action, String method, String endpoint, User user);
    List<Log> findAll();
}
