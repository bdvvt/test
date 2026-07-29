package com.example.test.models.mappers;

import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    User toEntity(RegisterReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserFromReq(UserReq req, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateProfileFromReq(ProfileUpdateReq req, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    void blockUserFromReq(BlockReq req, @MappingTarget User user);

    BlockRes toBlockRes(User user);

    UserRes toDto(User user);

    List<UserRes> toDtoList(List<User> users);
}
