package com.example.test.models.mappers;

import com.example.test.models.dto.req.PostCreateReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", ignore = true)
    Post toEntity(PostCreateReq req);

    PostRes toDto(Post post);
}
