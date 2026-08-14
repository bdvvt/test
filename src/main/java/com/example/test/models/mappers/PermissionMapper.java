package com.example.test.models.mappers;

import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = DepartmentIdMapper.class)
public interface PermissionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "departments", ignore = true)
    Permission toEntity(PermissionReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "departments", ignore = true)
    void updatePermissionFromReq(PermissionReq req, @MappingTarget Permission permission);

    @Mapping(target = "departmentIds", source = "departments")
    PermissionRes toDto(Permission permission);

    List<PermissionRes> toDtoList(List<Permission> permissions);
}
