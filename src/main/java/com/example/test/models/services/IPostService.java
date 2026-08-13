package com.example.test.models.services;
import com.example.test.models.dto.req.PostCreateReq;
import com.example.test.models.dto.res.PostRes;
public interface IPostService { PostRes create(PostCreateReq req); }
