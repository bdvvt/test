package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Permission;
import com.example.test.models.entities.Role;
import com.example.test.models.mappers.PermissionMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IPermissionRepository;
import com.example.test.models.services.IPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements IPermissionService {
    private final IPermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final IDepartmentRepository departmentRepository;

    @Override
    public PermissionRes createPermission(PermissionReq req) {
        List<Department> department = departmentRepository.findAllByIdIn(req.getDepartmentIds());
        Permission permission = permissionMapper.toEntity(req);
        permission.setDepartments(new HashSet<>(department));
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
        permissionRepository.delete(permission);
    }

    @Override
    public List<PermissionRes> findAll() {
        return permissionMapper.toDtoList(permissionRepository.findAll());
    }

    @Override
    public PermissionRes updatePermission(Long id, PermissionReq req) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
        List<Department> department = departmentRepository.findAllByIdIn(req.getDepartmentIds());
        permissionMapper.updatePermissionFromReq(req, permission);
        permission.setDepartments(new HashSet<>(department));
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

}
