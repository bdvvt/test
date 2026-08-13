package com.example.test.models.services.user;

import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.UserRes;

public interface UserCommandService {
    UserRes createUser(UserReq req);

    UserRes updateUser(Long id, UserReq req);

    void deleteUser(Long id);

    UserRes updateProfile(Long id, ProfileUpdateReq req);
}
