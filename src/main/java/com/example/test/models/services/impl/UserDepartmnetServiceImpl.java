package com.example.test.models.services.impl;

import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.UpdateRoleUser;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.entities.Department;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.entities.UserDepartmentRole;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserDepartmentRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.IUserDepartmentService;
import com.example.test.models.services.helper.UserDepartmentPermissionChecker;
import com.example.test.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDepartmnetServiceImpl implements IUserDepartmentService {
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final IRoleRepository roleRepository;
    private final IUserDepartmentRoleRepository scopeRepository;
    private final UserMapper userMapper;

    @Override
    public UserRes findByUserInDepartment(Long id, Long orgId, Long deptId) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("User không thuộc department"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserRes> listUsersInDepartment(Long orgId, Long deptId) {
        return userMapper.toDtoList(userRepository.findAllByOrganizationIdAndDepartmentId(orgId, deptId));
    }

    @Override
    public void deleteUserInDepartment(Long id, Long orgId, Long deptId) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("User không thuộc department"));
        user.setDepartment(null);
        userRepository.save(user);
    }

    @Override
    public UserRes updateUserRoleInDept(Long id, Long orgId, Long deptId, UpdateRoleUser req) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("User không thuộc department"));
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundException("Department không tồn tại"));
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());
        user.setRoles(new HashSet<>(roles));
        for (Role role : roles) {
            scopeRepository.save(UserDepartmentRole.builder().user(user).department(department).role(role).build());
        }
        return userMapper.toDto(userRepository.save(user));
    }
}
