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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDepartmnetServiceImpl implements IUserDepartmentService {
    private final IUserRepository userRepository;
    private final IDepartmentRepository departmentRepository;
    private final IRoleRepository roleRepository;
    private final IUserDepartmentRoleRepository userDepartmentRoleRepository;
    private final UserMapper userMapper;

    @Override
    public UserRes findByUserInDepartment(Long id, Long orgId, Long deptId) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("User không thuộc department"));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserRes> listUsersInDepartment(Long orgId, Long deptId) {
        return userMapper.toDtoList(
                userRepository.findAllByOrganizationIdAndDepartmentId(orgId, deptId)
        );
    }

    @Override
    public void deleteUserInDepartment(Long id, Long orgId, Long deptId) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("User không thuộc department"));
        user.setDepartment(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserRes updateUserRoleInDept(Long id,Long orgId,Long deptId,UpdateRoleUser req) {
        User user = userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new NotFoundException("User không thuộc organization"));
        Department department = departmentRepository.findByIdAndOrganizationId(deptId, orgId)
                .orElseThrow(() -> new NotFoundException("Department không tồn tại"));
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());
        if (roles.size() != req.getRoles().size()) {
            throw new NotFoundException("Role không tồn tại");
        }
        boolean hasDepartmentScopedPermission = roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .flatMap(permission -> permission.getDepartments().stream())
                .anyMatch(permissionDepartment -> deptId.equals(permissionDepartment.getId()));
        if (!hasDepartmentScopedPermission) {
            throw new NotFoundException("không tìm thấy department " + deptId);
        }
        userDepartmentRoleRepository.deleteAllByUserIdAndDepartmentId(id, deptId);
        userDepartmentRoleRepository.saveAll(
                roles.stream()
                        .map(role -> UserDepartmentRole.builder()
                                .user(user)
                                .department(department)
                                .role(role)
                                .build())
                        .toList()
        );
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void revokeUserRoleInDept(Long id, Long orgId, Long deptId, Long roleId) {
        userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new NotFoundException("User không thuộc organization"));
        departmentRepository.findByIdAndOrganizationId(deptId, orgId)
                .orElseThrow(() -> new NotFoundException("Department không tồn tại"));
        int deleted = userDepartmentRoleRepository
                .deleteByUserIdAndDepartmentIdAndRoleId(id, deptId, roleId);
        if (deleted == 0) {
            throw new NotFoundException("User chưa được gán role này trong department");
        }
    }
}
