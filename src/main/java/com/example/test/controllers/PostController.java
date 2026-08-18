package com.example.test.controllers;

import com.example.test.models.dto.req.PostReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IPostService;
import com.example.test.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails currentUser, @Valid @ModelAttribute PostReq req) {
        return ResponseEntity.status(201).body(
                ApiResponse.builder()
                        .message("Add New Role Successfully")
                        .code(201)
                        .data(postService.createPost(currentUser,req))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Get Post Successfully")
                        .code(200)
                        .data(postService.findAll(currentUser))
                        .build()
        );
    }
}
