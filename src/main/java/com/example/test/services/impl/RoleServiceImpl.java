package com.example.test.services.impl;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.entities.Role;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;

    @Override
    public Role createRole(RoleReq req) {
        Role role = Role.builder()
                .name(req.getName())
                .build();
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
