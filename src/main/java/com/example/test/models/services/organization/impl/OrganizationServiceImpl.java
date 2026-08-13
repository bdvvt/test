package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.OrganizationReq;
import com.example.test.models.dto.res.OrganizationRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Organization;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.OrganizationMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IOrganizationRepository;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IOrganizationService;
import com.example.test.security.jwt.JwtProvider;
import com.example.test.security.principal.CustomUserDetails;
import com.example.test.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements IOrganizationService {
    private final IOrganizationRepository organizationRepository;
    private final IDepartmentRepository departmentRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final OrganizationMapper organizationMapper;
    private final SecurityUtils securityUtils;

    @Override
    public OrganizationRes createOrganization(OrganizationReq req) {
        User currentUser = securityUtils.getCurrentUser();
        if (currentUser.getOrganization() != null) {
            throw new RuntimeException("Bạn đã sở hữu một công ty rồi, không thể tạo thêm!");
        }
        if (organizationRepository.existsByName(req.getName())) {
            throw new RuntimeException("Tên tổ chức đã được sử dụng");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(
                roleRepository.findByRoleName("ROLE_COMPANY_CREATOR")
                        .orElseThrow(() -> new NotFoundException("Role not found"))
        );
        log.info("Creating new organization entity to database for organization name: {}", req.getName());
        Organization organization = organizationMapper.toEntity(req);
        Organization savedOrg = organizationRepository.save(organization);
        currentUser.getRoles().addAll(roles);
        currentUser.setOrganization(savedOrg);
        userRepository.save(currentUser);
        return organizationMapper.toDto(savedOrg);
    }

    @Override
    public void deleteOrganization(Long id) {
        User currentUser = securityUtils.getCurrentUser();
        if (currentUser.getOrganization() == null || !currentUser.getOrganization().getId().equals(id)) {
            throw new RuntimeException("Bạn không có quyền thao tác trên công ty của người khác!");
        }
        log.info("Deleting organization record with ID: {}", id);
        Organization deleteOrganization = organizationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        organizationRepository.delete(deleteOrganization);
    }

    @Override
    public List<OrganizationRes> findAll() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizationMapper.toDtoList(organizations);
    }

    @Override
    public OrganizationRes findById(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        return organizationMapper.toDto(organization);
    }

    @Override
    public OrganizationRes updateOrganization(Long id, OrganizationReq req) {
        User currentUser = securityUtils.getCurrentUser();
        if (currentUser.getOrganization() == null || !currentUser.getOrganization().getId().equals(id)) {
            throw new RuntimeException("Bạn không có quyền thao tác trên công ty của người khác!");
        }
        Organization updateOrganization = organizationRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating organization record with ID: {}", id);
        organizationMapper.updateOrganizationFromReq(req, updateOrganization);
        return organizationMapper.toDto(organizationRepository.save(updateOrganization));
    }
}
