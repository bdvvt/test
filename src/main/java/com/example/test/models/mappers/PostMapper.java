package com.example.test.models.mappers;

import com.example.test.models.dto.req.PostReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post toEntity(PostReq req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updatePostFromReq(PostReq req, @MappingTarget Post post);

    PostRes toDto(Post post);

    List<PostRes> toDtoList(List<Post> posts);
}
