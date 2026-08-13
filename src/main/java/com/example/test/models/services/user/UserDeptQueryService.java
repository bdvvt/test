package com.example.test.models.services.user;

import com.example.test.models.dto.res.UserRes;

import java.util.List;

public interface UserDeptQueryService {
    UserRes findByUserInDepartment(Long id, Long orgId, Long deptId);

    List<UserRes> listUsersInDepartment(Long orgId, Long deptId);
}
