package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.PostReq;
import com.example.test.models.dto.res.PostRes;
import com.example.test.models.entities.Post;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.PostMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IOrganizationRepository;
import com.example.test.models.repositories.IPostRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IPostService;
import com.example.test.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final PostMapper postMapper;
    private final IOrganizationRepository organizationRepository;
    private final IDepartmentRepository departmentRepository;


    @Override
    public PostRes createPost(CustomUserDetails currentUser, PostReq req) {
        User user = userRepository.findById(currentUser.getUser().getId())
                .orElseThrow(() -> new NotFoundException("Not found id " + currentUser.getUser().getId()));
        Post post = postMapper.toEntity(req);
        post.setUser(user);
        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public List<PostRes> findAll(CustomUserDetails currentUser) {
        Long orgId = currentUser.getUser().getOrganization().getId();
        Long deptId = currentUser.getUser().getDepartment().getId();
        List<Post> posts = postRepository.findAllByUserOrganizationIdAndUserDepartmentId(orgId, deptId);
        return postMapper.toDtoList(posts);
    }
}
