package com.example.test.models.services.permission.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.entities.Permission;
import com.example.test.models.entities.Role;
import com.example.test.models.mappers.PermissionMapper;
import com.example.test.models.repositories.IPermissionRepository;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.services.permission.IPermissionService;
import com.example.test.models.services.role.IRoleService;
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

    @Override
    public PermissionRes createPermission(PermissionReq req) {
        String formattedPermissionName = normalizePermissionName(req.getName());
        if (permissionRepository.existsByName(formattedPermissionName)) {
            throw new RuntimeException("Permission đã tồn tại");
        }
        log.info("Creating new permission entity to database for permission name: {}", formattedPermissionName);
        Permission permission = permissionMapper.toEntity(req);
        permission.setName(formattedPermissionName);
        return permissionMapper.toDto(permissionRepository.save(permission));
    }

    @Override
    public void deletePermission(Long id) {
        Permission deletePermission = findPermission(id);
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
        Permission updatePermission = findPermission(id);
        log.info("Updating permission record with ID: {}", id);
        permissionMapper.updatePermissionFromReq(req, updatePermission);
        updatePermission.setName(normalizePermissionName(updatePermission.getName()));
        return permissionMapper.toDto(permissionRepository.save(updatePermission));
    }

    private String normalizePermissionName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        return name.trim().toUpperCase();
    }

    private Permission findPermission(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
    }
}
