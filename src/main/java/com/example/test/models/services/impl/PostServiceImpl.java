package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.PostCreateReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.entities.Post;
import com.example.test.models.mappers.PostMapper;
import com.example.test.models.repositories.IPostRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostRes create(PostCreateReq req) {
        if (!userRepository.existsById(req.getUserId())) {
            throw new NotFoundException("Not found user id " + req.getUserId());
        }
        Post post = postMapper.toEntity(req);
        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }
}
