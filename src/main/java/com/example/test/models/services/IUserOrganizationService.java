package com.example.test.models.services;

import com.example.test.models.dto.req.UserOrganizationReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.UserOrganizationRes;
import com.example.test.models.dto.res.UserRes;

import java.util.List;

public interface IUserOrganizationService {
    UserOrganizationRes findByIdInOrganization(Long id, Long orgId);
    UserOrganizationRes createUserInOrganization(Long orgId, UserOrganizationReq req);
    UserOrganizationRes updateUserInOrganization(Long id, Long orgId, UserOrganizationReq req);
    void deleteUserInOrganization(Long id, Long orgId);
    List<UserOrganizationRes> listUsersInOrganization(Long orgId);
}
