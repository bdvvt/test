package com.example.test.models.services.organization.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.OrganizationReq;
import com.example.test.models.dto.res.OrganizationRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Organization;
import com.example.test.models.mappers.OrganizationMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IOrganizationRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.organization.IOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements IOrganizationService {
    private final IOrganizationRepository organizationRepository;
    private final IDepartmentRepository departmentRepository;
    private final IUserRepository userRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationRes createOrganization(OrganizationReq req) {
        if (organizationRepository.existsByName(req.getName())) {
            throw new RuntimeException("Tên tổ chức đã được sử dụng");
        }
        log.info("Creating new organization entity to database for organization name: {}", req.getName());
        Organization organization = organizationMapper.toEntity(req);
        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Override
    public void deleteOrganization(Long id) {
        log.info("Deleting organization record with ID: {}", id);
        Organization deleteOrganization = findOrganization(id);
        organizationRepository.delete(deleteOrganization);
    }

    @Override
    public List<OrganizationRes> findAll() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizationMapper.toDtoList(organizations);
    }

    @Override
    public OrganizationRes findById(Long id) {
        Organization organization = findOrganization(id);
        return organizationMapper.toDto(organization);
    }

    @Override
    public OrganizationRes updateOrganization(Long id, OrganizationReq req) {
        Organization updateOrganization = findOrganization(id);
        log.info("Updating organization record with ID: {}", id);
        organizationMapper.updateOrganizationFromReq(req, updateOrganization);
        return organizationMapper.toDto(organizationRepository.save(updateOrganization));
    }

    private Organization findOrganization(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
    }
}
