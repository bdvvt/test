package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.UserOrganizationReq;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.dto.res.UserOrganizationRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Organization;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IOrganizationRepository;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IUserOrganizationService;
import com.example.test.models.services.uploads.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrganizationServiceImpl implements IUserOrganizationService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final IOrganizationRepository organizationRepository;
    private final IRoleRepository roleRepository;
    private final IDepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    @Override
    public UserRes findByIdInOrganization(Long id, Long orgId) {
        User user = userRepository.findByIdAndOrganizationId(id, orgId).orElseThrow(() -> new RuntimeException("không tìm thấy người dùng trong công ty"));
        return userMapper.toDto(user);
    }

    @Override
    public UserRes createUserInOrganization(Long orgId, UserOrganizationReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã được sử dụng");
        }
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        Department department = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bộ phận với ID: " + req.getDepartmentId()));
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tổ chức với ID: " + orgId));
        List<Role> role = roleRepository.findAllByIdIn(req.getRoles());
        log.info("Creating new user entity to database for full name: {}", req.getFullName());
        User user = userMapper.toOrgEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        user.setRoles(new HashSet<>(role));
        user.setDepartment(department);
        user.setOrganization(organization);
        user.setEnabled(true);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUserInOrganization(Long id, Long orgId) {
        log.info("Deleting user record with ID: {} in organization ID: {}", id, orgId);
        User deleteUser = userRepository.findByIdAndOrganizationId(id, orgId).orElseThrow(() -> new NotFoundException("Not found id " + id + " in organization " + orgId));
        userRepository.delete(deleteUser);
    }

    @Override
    public List<UserRes> listUsersInOrganization(Long orgId) {
        log.info("Listing all users for organization ID: {}", orgId);
        List<User> users = userRepository.findAllByOrganizationId(orgId);
        return userMapper.toDtoList(users);
    }

    @Override
    public UserRes updateUserInOrganization(Long id, Long orgId, UserOrganizationReq req) {
        User updateUser = userRepository.findByIdAndOrganizationId(id, orgId).orElseThrow(() -> new NotFoundException("Not found id " + id + " in organization " + orgId));
        log.info("Updating user record with ID: {}", id);
        Department department = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bộ phận với ID: " + req.getDepartmentId()));
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tổ chức với ID: " + orgId));
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());
        userMapper.updateUserInOrgFromReq(req, updateUser);
        updateUser.setPassword(passwordEncoder.encode(req.getPassword()));
        updateUser.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        updateUser.setRoles(new HashSet<>(roles));
        updateUser.setDepartment(department);
        updateUser.setOrganization(organization);
        updateUser.setEnabled(true);
        return userMapper.toDto(userRepository.save(updateUser));
    }
}
