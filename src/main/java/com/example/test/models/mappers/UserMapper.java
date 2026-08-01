package com.example.test.models.mappers;

import com.example.test.models.dto.req.*;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.UserOrganizationRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, DepartmentMapper.class, })
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    User toEntity(UserReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    User toEntity(RegisterReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    void updateUserFromReq(UserReq req, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    void updateProfileFromReq(ProfileUpdateReq req, @MappingTarget User user);

    BlockRes toBlockRes(User user);

    @Mapping(target = "avatarFile", source = "avatarUrl")
    UserRes toDto(User user);

    List<UserRes> toDtoList(List<User> users);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    User toOrgEntity(UserOrganizationReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "organization", ignore = true)
    void updateUserInOrgFromReq(UserOrganizationReq req, @MappingTarget User user);

    @Mapping(target = "avatarFile", source = "avatarUrl")
    UserOrganizationRes toOrgDto(User user);

    List<UserOrganizationRes> toOrgDtoList(List<User> users);
}
