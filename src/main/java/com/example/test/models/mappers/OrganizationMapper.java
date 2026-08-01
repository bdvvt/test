package com.example.test.models.mappers;

import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.req.OrganizationReq;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.OrganizationRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "departments", ignore = true)
    Organization toEntity(OrganizationReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "departments", ignore = true)
    void updateOrganizationFromReq(OrganizationReq req, @MappingTarget Organization organization);

    OrganizationRes toDto(Organization organization);

    List<OrganizationRes> toDtoList(List<Organization> organization);
}
