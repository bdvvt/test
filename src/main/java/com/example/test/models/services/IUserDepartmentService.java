package com.example.test.models.services;

import com.example.test.models.dto.req.UpdateRoleUser;
import com.example.test.models.dto.res.UserRes;
import java.util.List;

public interface IUserDepartmentService {
    UserRes findByUserInDepartment(Long id, Long orgId, Long deptId);
    void deleteUserInDepartment(Long id, Long orgId, Long deptId);
    List<UserRes> listUsersInDepartment(Long orgId, Long deptId);
    UserRes updateUserRoleInDept(Long id, Long orgId, Long deptId, UpdateRoleUser req);
    void revokeUserRoleInDept(Long id, Long orgId, Long deptId, Long roleId);
}
