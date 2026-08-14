package com.example.test.models.services;

import com.example.test.models.entities.User;

public interface ILogService {
    void recordAudit(String action, String method, String endpoint,
                     String description, User user);
}
