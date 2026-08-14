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
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.services.IPermissionService;
import com.example.test.models.services.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements IPermissionService {
    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;
    private final PermissionMapper permissionMapper;
    private final IDepartmentRepository departmentRepository;

    @Override
    public PermissionRes createPermission(PermissionReq req) {
        String formattedPermissionName = req.getName();
        if (formattedPermissionName != null && !formattedPermissionName.trim().isEmpty()) {
            formattedPermissionName = formattedPermissionName.trim().toUpperCase();

        }
        if (permissionRepository.existsByNameAndDepartmentId(formattedPermissionName, req.getDepartmentId())) {
            throw new RuntimeException("Permission đã tồn tại");
        }
        log.info("Creating new permission entity to database for permission name: {}", formattedPermissionName);
        Permission permission = permissionMapper.toEntity(req);
        permission.setName(formattedPermissionName);
        permission.setDepartment(findDepartment(req.getDepartmentId()));
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    public void deletePermission(Long id) {
        Permission deletePermission = permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Deleting permission record with ID: {}", id);
        permissionRepository.delete(deletePermission);
    }

    @Override
    public List<PermissionRes> findAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toDtoList(permissions);
    }

    @Override
    public PermissionRes updatePermission(Long id, PermissionReq req) {
        Permission updatePermission = permissionRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating permission record with ID: {}", id);
        permissionMapper.updatePermissionFromReq(req, updatePermission);
        if (updatePermission.getName() != null && !updatePermission.getName().trim().isEmpty()) {
            String formattedPermissionName = updatePermission.getName().trim().toUpperCase();
            updatePermission.setName(formattedPermissionName);
        }
        updatePermission.setDepartment(findDepartment(req.getDepartmentId()));
        return permissionMapper.toDto(permissionRepository.save(updatePermission));
    }

    private Department findDepartment(Long departmentId) {
        if (departmentId == null) return null;
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy department với ID: " + departmentId));
    }
}
