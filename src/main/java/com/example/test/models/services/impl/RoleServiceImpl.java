package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.RoleRes;
import com.example.test.models.entities.Role;
import com.example.test.models.mappers.RoleMapper;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.services.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleRes createRole(RoleReq req) {
        if (roleRepository.existsByCode(req.getCode())) {
            throw new RuntimeException("Mã role đã tồn tại");
        }
        if (roleRepository.existsByRoleName(req.getRoleName())) {
            throw new RuntimeException("Role đã tồn tại");
        }
        log.info("Creating new role entity to database for role name: {}", req.getRoleName());
        Role role = roleMapper.toEntity(req);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
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
        roleRepository.delete(deleteRole);
    }

    @Override
    public RoleRes updateRole(Long id, RoleReq req) {
        Role updateRole = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating role record with ID: {}", id);
        roleMapper.updateRoleFromReq(req, updateRole);
        return roleMapper.toDto(updateRole);
    }
}
