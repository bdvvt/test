package com.example.test.models.services.impl;

import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
import com.example.test.models.entities.ApplicationLog;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.ApplicationLogMapper;
import com.example.test.models.repositories.IApplicationLogRepository;
import com.example.test.models.services.ILogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {
    private final IApplicationLogRepository logRepository;
    private final ApplicationLogMapper applicationLogMapper;

    @Override
    public void recordAudit(String action, String method, String endpoint,
                             String description, User user) {
        LogCreateReq req = new LogCreateReq();
        req.setLevel("INFO");
        req.setMessage(description);
        req.setService("API");
        req.setAction(action);
        req.setMethod(method);
        req.setEndpoint(endpoint);
        req.setDescription(description);
        req.setUserId(user == null ? null : user.getId());
        req.setOrganizationId(user == null || user.getOrganization() == null
                ? null : user.getOrganization().getId());
        req.setDepartmentId(user == null || user.getDepartment() == null
                ? null : user.getDepartment().getId());
        req.setTimestamp(LocalDateTime.now());
        create(req);
    }

    @Override
    public LogRes create(LogCreateReq req) {
        ApplicationLog log = applicationLogMapper.toEntity(req);
        if (log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        ApplicationLog saved = logRepository.save(log);
        return applicationLogMapper.toDto(saved);
    }
}
