package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.RoleRes;
import com.example.test.models.entities.Permission;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.RoleMapper;
import com.example.test.models.repositories.IPermissionRepository;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.services.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final IPermissionRepository permissionRepository;

    @Override
    public RoleRes createRole(RoleReq req) {
        String formattedRoleName = req.getRoleName();
        if (formattedRoleName != null && !formattedRoleName.trim().isEmpty()) {
            formattedRoleName = formattedRoleName.trim().toUpperCase();
            if (!formattedRoleName.startsWith("ROLE_")) {
                formattedRoleName = "ROLE_" + formattedRoleName;
            }
        }
        if (roleRepository.existsByRoleName(formattedRoleName)) {
            throw new RuntimeException("Role đã tồn tại");
        }
        List<Permission> permissions = permissionRepository.findAllByIdIn(req.getPermissions());
        log.info("Creating new role entity to database for role name: {}", formattedRoleName);
        Role role = roleMapper.toEntity(req);
        role.setPermissions(new HashSet<>(permissions));
        role.setRoleName(formattedRoleName);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public List<RoleRes> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDtoList(roles);
    }

    @Override
    public void deleteRole(Long id) {
        Role deleteRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Deleting role record with ID: {}", id);
        for (User user : deleteRole.getUsers()) {
            user.getRoles().remove(deleteRole);
            user.getRoles().add(roleRepository.findByRoleName("ROLE_USER").orElseThrow(() -> new NotFoundException("Default role not found")));
        }
        roleRepository.delete(deleteRole);
    }

    @Override
    public RoleRes updateRole(Long id, RoleReq req) {
        Role updateRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        String formattedRoleName = req.getRoleName();
        if (formattedRoleName != null && !formattedRoleName.trim().isEmpty()) {
            formattedRoleName = formattedRoleName.trim().toUpperCase();
            if (!formattedRoleName.startsWith("ROLE_")) {
                formattedRoleName = "ROLE_" + formattedRoleName;
            }
        }
        List<Permission> permissions = permissionRepository.findAllByIdIn(req.getPermissions());
        log.info("Updating role record with ID: {}", id);
        roleMapper.updateRoleFromReq(req, updateRole);
        updateRole.setRoleName(formattedRoleName);
        updateRole.setPermissions(new HashSet<>(permissions));
        return roleMapper.toDto(roleRepository.save(updateRole));
    }
}
