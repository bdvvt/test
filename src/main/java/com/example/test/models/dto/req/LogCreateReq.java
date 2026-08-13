package com.example.test.models.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogCreateReq {
    @NotBlank
    private String level;

    @NotBlank
    private String message;

    @NotBlank
    private String service;

    private Long userId;

    private Map<String, Object> metadata;

    private LocalDateTime timestamp;
}
