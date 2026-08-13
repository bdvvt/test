package com.example.test.models.services.department;

import com.example.test.models.dto.req.AddManagerReq;
import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.ManagerRes;

import java.util.List;

public interface IDepartmentService {
    DepartmentRes createDepartment(Long orgId, DepartmentReq req);
    DepartmentRes updateDepartment(Long orgId,Long id, DepartmentReq req);
    DepartmentRes findById(Long id, Long orgId);
    List<DepartmentRes> findAll(Long orgId);
    void deleteDepartment(Long id, Long orgId);
}
