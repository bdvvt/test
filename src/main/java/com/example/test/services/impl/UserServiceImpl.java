package com.example.test.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public User createUser(UserReq req) {
        Role role = roleRepository.findById(req.getRoleId()).orElseThrow(() -> new NotFoundException("Not found id " + req.getRoleId()));
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .phoneNumber(req.getPhoneNumber())
                .dateOfBirth(req.getDateOfBirth())
                .role(role)
                .build();
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User deleteUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        userRepository.delete(deleteUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
    }

    @Override
    public User updateUser(Long id, UserReq req) {
        Role role = roleRepository.findById(req.getRoleId()).orElseThrow(() -> new NotFoundException("Not found id " + req.getRoleId()));
        User updateUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        updateUser.setFullName(req.getFullName());
        updateUser.setEmail(req.getEmail());
        updateUser.setPhoneNumber(req.getPhoneNumber());
        updateUser.setDateOfBirth(req.getDateOfBirth());
        updateUser.setRole(role);
        return userRepository.save(updateUser);
    }
}
