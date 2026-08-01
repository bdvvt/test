package com.example.test.models.services;

import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.res.DepartmentRes;

import java.util.List;

public interface IDepartmentService {
    DepartmentRes createDepartment(DepartmentReq req);
    DepartmentRes updateDepartment(Long id, DepartmentReq req);
    DepartmentRes findById(Long id);
    List<DepartmentRes> findAll();
    void deleteDepartment(Long id);
}
