package com.example.test.models.mappers;

import com.example.test.models.dto.req.RoleReq;
import com.example.test.models.dto.res.RoleRes;
import com.example.test.models.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateRoleFromReq(RoleReq req, @MappingTarget Role role);

    RoleRes toDto(Role role);

    List<RoleRes> toDtoList(List<Role> roles);
}
