package com.example.test.models.services.impl;

import com.example.test.exceptions.AuthException;
import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.LoginReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.LoginRes;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IAuthService;
import com.example.test.security.jwt.JwtProvider;
import com.example.test.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final IRoleRepository roleRepository;

    @Override
    public void register(RegisterReq req) {
        Set<Role> roles = new HashSet<>();
        roles.add(
                roleRepository.findByRoleName("ROLE_USER")
                        .orElseThrow(() -> new NotFoundException("Role not found"))
        );
        User user =userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        userMapper.toDto(savedUser);
    }

    @Override
    public LoginRes login(LoginReq req) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (LockedException e) {
            throw new AuthException("Tài khoản của bạn đã bị khóa");
        } catch (AuthenticationException e) {
            throw new AuthException("Mật khẩu hoặc tài khoản không đúng");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userDetails);
        return LoginRes.builder()
                .fullName(userDetails.getUser().getFullName())
                .username(userDetails.getUsername())
                .email(userDetails.getUser().getEmail())
                .phoneNumber(userDetails.getUser().getPhoneNumber())
                .dateOfBirth(userDetails.getUser().getDateOfBirth())
                .roles(userDetails.getUser().getRoles()
                        .stream().map(role -> role.getRoleName())
                        .collect(Collectors.toSet())
                )
                .accessToken(token)
                .type("Bearer")
                .build();


    }

    @Override
    public BlockRes toggleBlockUser(Long id, boolean blockStatus) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Người dùng đã bị khóa"));
        log.info("Blocking user record with ID: {}", id);
        user.setBlock(blockStatus);
        return userMapper.toBlockRes(userRepository.save(user));
    }
}
