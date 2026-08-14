package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Permission;
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
        String name = normalizeName(req.getName());
        if (permissionRepository.existsByName(name)) {
            throw new RuntimeException("Permission đã tồn tại");
        }
        Permission permission = permissionMapper.toEntity(req);
        permission.setName(name);
        permission.setDepartments(findDepartments(req.getDepartmentIds()));
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
        permissionMapper.updatePermissionFromReq(req, permission);
        permission.setName(normalizeName(req.getName()));
        permission.setDepartments(findDepartments(req.getDepartmentIds()));
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    private String normalizeName(String name) {
        return name.trim().toUpperCase();
    }

    private Set<Department> findDepartments(Set<Long> departmentIds) {
        if (departmentIds == null || departmentIds.isEmpty()) return Set.of();
        List<Department> departments = departmentRepository.findAllById(departmentIds);
        if (departments.size() != departmentIds.size()) {
            throw new NotFoundException("Có departmentId không tồn tại");
        }
        return new HashSet<>(departments);
    }
}
