package com.example.test.models.services;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.RoleRes;

import java.util.List;


public interface IRoleService {
    RoleRes createRole(RoleReq req);
    List<RoleRes> findAll();
    void deleteRole(Long id);
    RoleRes updateRole(Long id, RoleReq req);
}
