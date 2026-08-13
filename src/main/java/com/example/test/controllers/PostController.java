package com.example.test.controllers;

import com.example.test.models.dto.req.PostCreateReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.dto.wrapper.ApiResponse;
import com.example.test.models.services.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostRes>> create(@Valid @RequestBody PostCreateReq req) {
        return ResponseEntity.ok(ApiResponse.<PostRes>builder()
                .message("Post created successfully").code(200).data(postService.create(req)).build());
    }
}
