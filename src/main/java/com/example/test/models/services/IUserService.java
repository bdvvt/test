package com.example.test.models.services;

import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.User;

import java.util.List;

public interface IUserService {
    UserRes createUser(UserReq req);
    UserRes updateUser(Long id, UserReq req);
    UserRes findById(Long id);
    List<UserRes> findAll();
    void deleteUser(Long id);
    UserRes updateProfile(Long id, ProfileUpdateReq req);
    BlockRes toggleBlockUser(Long id, BlockReq status);
    List<DepartmentRes> getManagedDepartmentsByMember(Long memberId);
}
