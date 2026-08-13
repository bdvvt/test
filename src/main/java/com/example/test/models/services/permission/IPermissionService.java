package com.example.test.models.services.permission;

import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.dto.res.RoleRes;

import java.util.List;

public interface IPermissionService {
    PermissionRes createPermission(PermissionReq req);
    List<PermissionRes> findAll();
    void deletePermission(Long id);
    PermissionRes updatePermission(Long id, PermissionReq req);
}
