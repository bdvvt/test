package com.example.test.controllers;

import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.ILogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
public class LogController {
    private final ILogService logService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        log.info("Fetching all organizations");
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Organization Successfully")
                        .code(200)
                        .data(logService.findAll())
                        .build()
        );
    }
}
