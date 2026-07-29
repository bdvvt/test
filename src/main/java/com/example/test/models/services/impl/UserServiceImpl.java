package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserRes createUser(UserReq req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        List<Role> role = roleRepository.findAllByIdIn(req.getRoles());
        if (role.isEmpty()) {
            throw new NotFoundException("Không tìm thấy các role có trg ID");
        }
        log.info("Creating new user entity to database for full name: {}", req.getFullName());
        User user = userMapper.toEntity(req);
        user.setRoles(new HashSet<>(role));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user record with ID: {}", id);
        User deleteUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        userRepository.delete(deleteUser);
    }

    @Override
    public List<UserRes> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserRes findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public UserRes updateUser(Long id, UserReq req) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating user record with ID: {}", id);
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());
        if (roles.isEmpty()) {
            throw new NotFoundException("Không tìm thấy các role có trg ID");
        }
        userMapper.updateUserFromReq(req, updateUser);
        updateUser.setRoles(new HashSet<>(roles));

        User savedUser = userRepository.save(updateUser);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserRes updateProfile(Long id, ProfileUpdateReq req) {
        User updateProfile = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating profile record with ID: {}", id);
        userMapper.updateProfileFromReq(req, updateProfile);
        User savedUser = userRepository.save(updateProfile);
        return userMapper.toDto(savedUser);
    }


}
