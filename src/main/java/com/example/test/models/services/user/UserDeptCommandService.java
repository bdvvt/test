package com.example.test.models.services.user;

import com.example.test.models.dto.req.UpdateRoleUser;
import com.example.test.models.dto.res.UserRes;

public interface UserDeptCommandService {
    void deleteUserInDepartment(Long id, Long orgId, Long deptId);

    UserRes updateUserRoleInDept(Long id, Long orgId, Long deptId, UpdateRoleUser req);
}
