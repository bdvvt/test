package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.DepartmentRes;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Organization;
import com.example.test.models.mappers.DepartmentMapper;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.dto.req.UserReq;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.repositories.*;
import com.example.test.models.services.IUserService;
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
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IDepartmentRepository departmentRepository;
    private final UserMapper userMapper;
    private final IOrganizationRepository organizationRepository;
    private final UploadService uploadService;
    private final DepartmentMapper departmentMapper;

    @Override
    public UserRes createUser(UserReq req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã được sử dụng");
        }
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        List<Role> role = roleRepository.findAllByIdIn(req.getRoles());
        Department department = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bộ phận với ID: " + req.getDepartmentId()));
        Organization organization = organizationRepository.findById(req.getOrganizationId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tổ chức với ID: " + req.getOrganizationId()));
        log.info("Creating new user entity to database for full name: {}", req.getFullName());
        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        user.setRoles(new HashSet<>(role));
        user.setDepartment(department);
        user.setOrganization(organization);
        user.setEnabled(true);
        return userMapper.toDto(userRepository.save(user));
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
        Department department = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bộ phận với ID: " + req.getDepartmentId()));
        Organization organization = organizationRepository.findById(req.getOrganizationId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tổ chức với ID: " + req.getOrganizationId()));
        userMapper.updateUserFromReq(req, updateUser);
        updateUser.setPassword(passwordEncoder.encode(req.getPassword()));
        updateUser.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        updateUser.setRoles(new HashSet<>(roles));
        updateUser.setDepartment(department);
        updateUser.setOrganization(organization);
        updateUser.setEnabled(true);
        return userMapper.toDto(userRepository.save(updateUser));
    }

    @Override
    public UserRes updateProfile(Long id, ProfileUpdateReq req) {
        User updateProfile = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found id " + id));
        log.info("Updating profile record with ID: {}", id);
        updateProfile.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        userMapper.updateProfileFromReq(req, updateProfile);
        return userMapper.toDto(userRepository.save(updateProfile));
    }

    @Override
    public BlockRes toggleBlockUser(Long id, BlockReq req) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Người dùng đã bị khóa"));
        log.info("Blocking user record with ID: {}", id);
        user.setBlock(req.isBlock());
        return userMapper.toBlockRes(userRepository.save(user));
    }

    @Override
    public List<DepartmentRes> getManagedDepartmentsByMember(Long memberId) {
        if (!userRepository.existsById(memberId)) {
            throw new NotFoundException("Không tìm thấy người dùng ID: " + memberId);
        }

        List<Department> departments = departmentRepository.findManagedDepartmentsByMemberId(memberId);

        return departmentMapper.toDtoList(departments);
    }
}
