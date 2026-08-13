package com.example.test.models.services.user;

import com.example.test.models.dto.req.UserOrganizationReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.UserOrganizationRes;
import com.example.test.models.dto.res.UserRes;

import java.util.List;

public interface IUserOrganizationService {
    UserRes findByIdInOrganization(Long id, Long orgId);
    UserRes createUserInOrganization(Long orgId, UserOrganizationReq req);
    UserRes updateUserInOrganization(Long id, Long orgId, UserOrganizationReq req);
    void deleteUserInOrganization(Long id, Long orgId);
    List<UserRes> listUsersInOrganization(Long orgId);
}
