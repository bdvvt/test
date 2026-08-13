package com.example.test.models.services.department.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.DepartmentMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.services.department.IDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements IDepartmentService {
    private final IDepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    @Override
    public DepartmentRes createDepartment(DepartmentReq req) {
        if (departmentRepository.existsByDepartmentName(req.getDepartmentName())) {
            throw new RuntimeException("Tên phòng ban đã được sử dụng");
        }
        log.info("Creating new department entity to database for department name: {}", req.getDepartmentName());
        Department department = departmentMapper.toEntity(req);
        return departmentMapper.toDto(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        log.info("Deleting department record with ID: {}", id);
        Department deleteDepartment = findDepartment(id);
        departmentRepository.delete(deleteDepartment);
    }

    @Override
    public List<DepartmentRes> findAll() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDtoList(departments);
    }

    @Override
    public DepartmentRes findById(Long id) {
        Department department = findDepartment(id);
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentRes updateDepartment(Long id, DepartmentReq req) {
        Department updateDepartment = findDepartment(id);
        log.info("Updating department record with ID: {}", id);
        departmentMapper.updateDepartmentFromReq(req, updateDepartment);
        return departmentMapper.toDto(departmentRepository.save(updateDepartment));
    }

    private Department findDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
    }



}
