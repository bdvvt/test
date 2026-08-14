package com.example.test.models.mappers;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.RoleRes;
import com.example.test.models.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "department", ignore = true)
    Role toEntity(RoleReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "department", ignore = true)
    void updateRoleFromReq(RoleReq req, @MappingTarget Role role);

    @Mapping(target = "departmentId", source = "department.id")
    RoleRes toDto(Role role);

    List<RoleRes> toDtoList(List<Role> roles);
}
