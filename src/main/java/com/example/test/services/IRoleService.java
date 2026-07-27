package com.example.test.services;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.entities.Role;

import java.util.List;


public interface IRoleService {
    Role createRole(RoleReq req);
    List<Role> findAll();
}
