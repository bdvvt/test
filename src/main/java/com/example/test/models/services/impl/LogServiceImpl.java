package com.example.test.models.services.impl;

import com.example.test.models.dto.req.AuditLogData;
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
        AuditLogData data = AuditLogData.builder()
                .level("INFO")
                .message(description)
                .service("API")
                .action(action)
                .method(method)
                .endpoint(endpoint)
                .description(description)
                .userId(user == null ? null : user.getId())
                .organizationId(user == null || user.getOrganization() == null
                        ? null : user.getOrganization().getId())
                .departmentId(user == null || user.getDepartment() == null
                        ? null : user.getDepartment().getId())
                .timestamp(LocalDateTime.now())
                .build();

        ApplicationLog log = applicationLogMapper.toEntity(data);
        logRepository.save(log);
    }
}
