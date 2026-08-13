package com.example.test.models.mappers;

import com.example.test.models.dto.req.AddManagerReq;
import com.example.test.models.dto.req.DepartmentReq;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.ManagerRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface DepartmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "organization", ignore = true)
    Department toEntity(DepartmentReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "organization", ignore = true)
    void updateDepartmentFromReq(DepartmentReq req, @MappingTarget Department department);

    DepartmentRes toDto(Department department);

    List<DepartmentRes> toDtoList(List<Department> departments);

}
