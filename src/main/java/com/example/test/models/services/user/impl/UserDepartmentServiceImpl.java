package com.example.test.models.services.user.impl;

import com.example.test.exceptions.DataConflictException;
import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.UpdateRoleUser;
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
import com.example.test.models.services.user.IUserDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDepartmentServiceImpl implements IUserDepartmentService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final IRoleRepository roleRepository;
    private final IDepartmentRepository departmentRepository;
    private final IOrganizationRepository organizationRepository;

    @Override
    public UserRes findByUserInDepartment(Long id, Long orgId, Long deptId) {
        User user = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("Not found id " + id + " in organization " + orgId + " and department " + deptId));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUserInDepartment(Long id, Long orgId, Long deptId) {
        User deleteUser = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("Not found id " + id + " in organization " + orgId + " and department " + deptId));
        userRepository.delete(deleteUser);
    }

    @Override
    public List<UserRes> listUsersInDepartment(Long orgId, Long deptId) {
        List<User> users = userRepository.findAllByOrganizationIdAndDepartmentId(orgId, deptId);
        return userMapper.toDtoList(users);
    }

    @Override
    public UserRes updateUserRoleInDept(Long id, Long orgId, Long deptId, UpdateRoleUser req) {
        User updateUser = userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException("Not found id " + id + " in organization " + orgId + " and department " + deptId));
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bộ phận với ID: " + deptId));
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tổ chức với ID: " + orgId));
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());
        boolean isManager = roles.stream().anyMatch(r -> r.getRoleName().equalsIgnoreCase("ROLE_MANAGER"));
        if (isManager && userRepository.existsByDepartmentIdAndRolesRoleNameAndIdNot(deptId, "ROLE_MANAGER", id)) {
            throw new DataConflictException("Phòng ban này đã có Manager!");
        }
        updateUser.setRoles(new HashSet<>(roles));
        updateUser.setDepartment(department);
        updateUser.setOrganization(organization);
        return userMapper.toDto(userRepository.save(updateUser));
    }
}
