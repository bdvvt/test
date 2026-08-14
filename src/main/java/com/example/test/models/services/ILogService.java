package com.example.test.models.services;
import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
import com.example.test.models.entities.User;
public interface ILogService {
    LogRes create(LogCreateReq req);

    void recordAudit(String action, String method, String endpoint,
                     String description, User user);
}
