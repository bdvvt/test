package com.example.test.models.services;

import com.example.test.models.dto.req.OrganizationReq;
import com.example.test.models.dto.res.OrganizationRes;

import java.util.List;

public interface IOrganizationService {
    OrganizationRes createOrganization(OrganizationReq req);
    OrganizationRes updateOrganization(Long id, OrganizationReq req);
    OrganizationRes findById(Long id);
    List<OrganizationRes> findAll();
    void deleteOrganization(Long id);
}
