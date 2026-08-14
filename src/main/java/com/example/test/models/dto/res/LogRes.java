package com.example.test.models.dto.res;

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
public class LogRes {
    private Long id;
    private String level;
    private String message;
    private String service;
    private String action;
    private String method;
    private String endpoint;
    private Long userId;
    private Map<String, Object> metadata;
    private LocalDateTime timestamp;
}
