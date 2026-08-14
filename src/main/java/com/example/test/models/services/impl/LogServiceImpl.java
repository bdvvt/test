package com.example.test.models.services.impl;

import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
import com.example.test.models.entities.ApplicationLog;
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
    public LogRes create(LogCreateReq req) {
        ApplicationLog log = applicationLogMapper.toEntity(req);
        if (log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        ApplicationLog saved = logRepository.save(log);
        return applicationLogMapper.toDto(saved);
    }
}
