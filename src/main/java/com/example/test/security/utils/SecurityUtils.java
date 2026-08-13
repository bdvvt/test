package com.example.test.security.utils;

import com.example.test.models.entities.User;
import com.example.test.security.principal.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        }

        throw new RuntimeException("Chưa đăng nhập hoặc phiên làm việc không hợp lệ!");
    }
}
