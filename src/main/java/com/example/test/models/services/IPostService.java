package com.example.test.models.services;

import com.example.test.models.dto.req.PostReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.security.principal.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.List;

public interface IPostService {
    PostRes createPost(@AuthenticationPrincipal CustomUserDetails currentUser, PostReq req);
//    PostRes updatePost(Long id, PostReq req);
    List<PostRes> findAll(CustomUserDetails currentUser);
//    void deletePost(Long id);
}
