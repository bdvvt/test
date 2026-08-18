package com.example.test.models.services.impl;

import com.example.test.models.dto.req.AuditLogData;
import com.example.test.models.entities.Log;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.ApplicationLogMapper;
import com.example.test.models.repositories.IApplicationLogRepository;
import com.example.test.models.services.ILogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {
    private final IApplicationLogRepository logRepository;
    private final ApplicationLogMapper applicationLogMapper;

    @Override
    public void recordAudit(String action, String method, String endpoint, User user) {
        AuditLogData data = AuditLogData.builder()
                .level("INFO")
                .message(action)
                .service("API")
                .action(action)
                .method(method)
                .endpoint(endpoint)
                .userId(user == null ? null : user.getId())
                .organizationId(user == null || user.getOrganization() == null
                        ? null : user.getOrganization().getId())
                .departmentId(user == null || user.getDepartment() == null
                        ? null : user.getDepartment().getId())
                .timestamp(LocalDateTime.now())
                .build();

        Log log = applicationLogMapper.toEntity(data);
        logRepository.save(log);
    }

    @Override
    public void recordError(
            String action,
            String method,
            String endpoint,
            Throwable exception,
            User user
    ) {
        String message = exception.getMessage();
        if (message == null || message.isBlank()) {
            message = exception.getClass().getSimpleName();
        }

        AuditLogData data = AuditLogData.builder()
                .level("ERROR")
                .message(message)
                .service("API")
                .action(action)
                .method(method)
                .endpoint(endpoint)
                .userId(user == null ? null : user.getId())
                .organizationId(user == null || user.getOrganization() == null
                        ? null : user.getOrganization().getId())
                .departmentId(user == null || user.getDepartment() == null
                        ? null : user.getDepartment().getId())
                .timestamp(LocalDateTime.now())
                .build();

        Log log = applicationLogMapper.toEntity(data);
        logRepository.save(log);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }
}
