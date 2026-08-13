package com.example.test.security.utils;


import com.example.test.models.entities.User;
import com.example.test.models.repositories.IDepartmentRepository;
import com.example.test.models.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DepartmentAccessUtils {
    private final IDepartmentRepository departmentRepository;
    private final IUserRepository userRepository;
    private final SecurityUtils securityUtils;

    public boolean canAccessUser(Long userId) {
        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getId().equals(userId)) {
            return true;
        }

        return userRepository.isManagerOfUserDepartment(currentUser.getId(), userId);
    }

    public List<Long> getManagedDepartmentIds() {
        User currentUser = securityUtils.getCurrentUser();
        return departmentRepository.findDepartmentIdsByMemberId(currentUser.getId());
    }
}