package com.example.test.models.services.user.impl;

import com.example.test.exceptions.DataConflictException;
import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.req.UpdateRoleUser;
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
import com.example.test.models.services.uploads.UploadService;
import com.example.test.models.services.user.IUserDepartmentService;
import com.example.test.models.services.user.IUserOrganizationService;
import com.example.test.models.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, IUserOrganizationService, IUserDepartmentService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IDepartmentRepository departmentRepository;
    private final UserMapper userMapper;
    private final IOrganizationRepository organizationRepository;
    private final UploadService uploadService;

    // ==================== UserService ====================

    @Override
    public UserRes createUser(UserReq req) {
        validateNewUser(req.getUsername(), req.getEmail());

        UserRelations relations = loadUserRelations(
                req.getOrganizationId(),
                req.getDepartmentId(),
                req.getRoles()
        );

        log.info("Creating new user entity to database for full name: {}", req.getFullName());
        User user = userMapper.toEntity(req);
        applyPassword(user, req.getPassword());
        applyAvatar(user, req.getAvatarFile());
        applyUserRelations(user, relations);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user record with ID: {}", id);
        userRepository.delete(findUserById(id));
    }

    @Override
    public List<UserRes> findAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserRes findById(Long id) {
        return userMapper.toDto(findUserById(id));
    }

    @Override
    public UserRes updateUser(Long id, UserReq req) {
        User user = findUserById(id);
        log.info("Updating user record with ID: {}", id);

        UserRelations relations = loadUserRelations(
                req.getOrganizationId(),
                req.getDepartmentId(),
                req.getRoles()
        );

        userMapper.updateUserFromReq(req, user);
        applyPassword(user, req.getPassword());
        applyAvatar(user, req.getAvatarFile());
        applyUserRelations(user, relations);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserRes updateProfile(Long id, ProfileUpdateReq req) {
        User user = findUserById(id);
        log.info("Updating profile record with ID: {}", id);
        user.setAvatarUrl(uploadService.upload(req.getAvatarFile()));
        userMapper.updateProfileFromReq(req, user);
        return userMapper.toDto(userRepository.save(user));
    }

    // ==================== UserOrganizationService ====================

    @Override
    public UserOrganizationRes findByIdInOrganization(Long id, Long orgId) {
        return userMapper.toOrgDto(findUserInOrganization(id, orgId));
    }

    @Override
    public UserOrganizationRes createUserInOrganization(
            Long orgId,
            UserOrganizationReq req
    ) {
        validateNewUser(req.getUsername(), req.getEmail());

        UserRelations relations = loadUserRelations(
                orgId,
                req.getDepartmentId(),
                req.getRoles()
        );

        log.info("Creating new user entity to database for full name: {}", req.getFullName());
        User user = userMapper.toOrgEntity(req);
        applyPassword(user, req.getPassword());
        applyAvatar(user, req.getAvatarFile());
        applyUserRelations(user, relations);

        return userMapper.toOrgDto(userRepository.save(user));
    }

    @Override
    public UserOrganizationRes updateUserInOrganization(
            Long id,
            Long orgId,
            UserOrganizationReq req
    ) {
        User user = findUserInOrganization(id, orgId);
        log.info("Updating user record with ID: {}", id);

        UserRelations relations = loadUserRelations(
                orgId,
                req.getDepartmentId(),
                req.getRoles()
        );

        userMapper.updateUserInOrgFromReq(req, user);
        applyPassword(user, req.getPassword());
        applyAvatar(user, req.getAvatarFile());
        applyUserRelations(user, relations);

        return userMapper.toOrgDto(userRepository.save(user));
    }

    @Override
    public void deleteUserInOrganization(Long id, Long orgId) {
        log.info("Deleting user record with ID: {} in organization ID: {}", id, orgId);
        userRepository.delete(findUserInOrganization(id, orgId));
    }

    @Override
    public List<UserOrganizationRes> listUsersInOrganization(Long orgId) {
        log.info("Listing all users for organization ID: {}", orgId);
        return userMapper.toOrgDtoList(userRepository.findAllByOrganizationId(orgId));
    }

    // ==================== UserDepartmentService ====================

    @Override
    public UserRes findByUserInDepartment(Long id, Long orgId, Long deptId) {
        return userMapper.toDto(findUserInDepartment(id, orgId, deptId));
    }

    @Override
    public void deleteUserInDepartment(Long id, Long orgId, Long deptId) {
        log.info("Deleting user record with ID: {} in organization {} and department {}", id, orgId, deptId);
        userRepository.delete(findUserInDepartment(id, orgId, deptId));
    }

    @Override
    public List<UserRes> listUsersInDepartment(Long orgId, Long deptId) {
        return userMapper.toDtoList(
                userRepository.findAllByOrganizationIdAndDepartmentId(orgId, deptId)
        );
    }

    @Override
    public UserRes updateUserRoleInDept(
            Long id,
            Long orgId,
            Long deptId,
            UpdateRoleUser req
    ) {
        User user = findUserInDepartment(id, orgId, deptId);
        Department department = findDepartment(deptId);
        Organization organization = findOrganization(orgId);
        List<Role> roles = roleRepository.findAllByIdIn(req.getRoles());

        boolean isManager = roles.stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ROLE_MANAGER"));
        if (isManager && userRepository.existsByDepartmentIdAndRolesRoleNameAndIdNot(
                deptId,
                "ROLE_MANAGER",
                id
        )) {
            throw new DataConflictException("Phòng ban này đã có Manager!");
        }

        user.setRoles(new HashSet<>(roles));
        user.setDepartment(department);
        user.setOrganization(organization);
        return userMapper.toDto(userRepository.save(user));
    }

    // ==================== Shared private helpers ====================

    private void validateNewUser(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Tên đăng nhập đã được sử dụng");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }
    }

    private UserRelations loadUserRelations(
            Long organizationId,
            Long departmentId,
            List<Long> roleIds
    ) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy tổ chức với ID: " + organizationId
                ));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy bộ phận với ID: " + departmentId
                ));

        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        return new UserRelations(organization, department, roles);
    }

    private void applyUserRelations(User user, UserRelations relations) {
        user.setOrganization(relations.organization());
        user.setDepartment(relations.department());
        user.setRoles(new HashSet<>(relations.roles()));
    }

    private void applyPassword(User user, String rawPassword) {
        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
    }

    private void applyAvatar(User user, MultipartFile avatarFile) {
        Optional.ofNullable(avatarFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> user.setAvatarUrl(uploadService.upload(file)));
    }

    private User findUserInDepartment(Long id, Long orgId, Long deptId) {
        return userRepository.findByIdAndOrganizationIdAndDepartmentId(id, orgId, deptId)
                .orElseThrow(() -> new NotFoundException(
                        "Not found id " + id + " in organization " + orgId + " and department " + deptId
                ));
    }

    private Department findDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy bộ phận với ID: " + departmentId
                ));
    }

    private Organization findOrganization(Long organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(
                        "Không tìm thấy tổ chức với ID: " + organizationId
                ));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found id " + id));
    }

    private User findUserInOrganization(Long id, Long orgId) {
        return userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new NotFoundException(
                        "Not found id " + id + " in organization " + orgId
                ));
    }

    private record UserRelations(
            Organization organization,
            Department department,
            List<Role> roles
    ) {
    }
}
