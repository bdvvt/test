package com.example.test.services;

import com.example.test.models.dto.req.UserReq;
import com.example.test.models.entities.User;

import java.util.List;

public interface IUserService {
    User createUser(UserReq req);
    User updateUser(Long id, UserReq req);
    User findById(Long id);
    List<User> findAll();
    void deleteUser(Long id);
}
