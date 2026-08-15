package com.example.test.models.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogData {
    private String level;
    private String service;
    private String action;
    private String method;
    private String endpoint;
    private Long userId;
    private Long organizationId;
    private Long departmentId;
    private LocalDateTime timestamp;
}
