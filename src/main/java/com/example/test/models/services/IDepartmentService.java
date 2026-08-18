package com.example.test.models.services;

import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.res.DepartmentRes;

import java.util.List;

public interface IDepartmentService {
    DepartmentRes createDepartment(Long orgId, DepartmentReq req);
    DepartmentRes updateDepartment(Long orgId,Long id, DepartmentReq req);
    DepartmentRes findById(Long id, Long orgId);
    List<DepartmentRes> findAll(Long orgId);
    void deleteDepartment(Long id, Long orgId);
}
