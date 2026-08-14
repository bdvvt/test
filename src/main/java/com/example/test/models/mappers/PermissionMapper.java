package com.example.test.models.mappers;

import com.example.test.models.dto.req.PermissionReq;
import com.example.test.models.dto.res.PermissionRes;
import com.example.test.models.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "departments", ignore = true)
    Permission toEntity(PermissionReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "departments", ignore = true)
    void updatePermissionFromReq(PermissionReq req, @MappingTarget Permission permission);

    default PermissionRes toDto(Permission permission) {
        Set<Long> departmentIds = permission.getDepartments() == null
                ? Set.of()
                : permission.getDepartments().stream()
                .map(department -> department.getId())
                .collect(Collectors.toSet());

        return PermissionRes.builder()
                .id(permission.getId())
                .name(permission.getName())
                .departmentIds(departmentIds)
                .build();
    }

    default List<PermissionRes> toDtoList(List<Permission> permissions) {
        return permissions.stream().map(this::toDto).toList();
    }
}
