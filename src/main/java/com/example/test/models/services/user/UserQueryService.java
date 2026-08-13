package com.example.test.models.services.user;

import com.example.test.models.dto.res.UserRes;

import java.util.List;

public interface UserQueryService {
    UserRes findById(Long id);

    List<UserRes> findAll();
}
