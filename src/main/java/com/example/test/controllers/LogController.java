package com.example.test.controllers;

import com.example.test.models.dto.req.LogCreateReq;
import com.example.test.models.dto.res.LogRes;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.ILogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {
    private final ILogService logService;

    @PostMapping
    public ResponseEntity<ApiResponse<LogRes>> create(@Valid @RequestBody LogCreateReq req) {
        return ResponseEntity.ok(ApiResponse.<LogRes>builder()
                .message("Log created successfully").code(200).data(logService.create(req)).build());
    }
}
