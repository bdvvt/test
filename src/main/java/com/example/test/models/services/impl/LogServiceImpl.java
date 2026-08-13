package com.example.test.models.services.impl;

import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
import com.example.test.models.entities.ApplicationLog;
import com.example.test.models.repositories.IApplicationLogRepository;
import com.example.test.models.services.ILogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {
    private final IApplicationLogRepository logRepository;

    @Override
    public LogRes create(LogCreateReq req) {
        ApplicationLog saved = logRepository.save(ApplicationLog.builder()
                .level(req.getLevel())
                .message(req.getMessage())
                .service(req.getService())
                .userId(req.getUserId())
                .metadata(req.getMetadata())
                .timestamp(req.getTimestamp() == null ? LocalDateTime.now() : req.getTimestamp())
                .build());
        return LogRes.builder().id(saved.getId()).level(saved.getLevel())
                .message(saved.getMessage()).service(saved.getService()).userId(saved.getUserId())
                .metadata(saved.getMetadata()).timestamp(saved.getTimestamp()).build();
    }
}
