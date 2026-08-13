package com.example.test.models.services.schedules;

import com.example.test.models.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCleanupSchedule {
    private final IUserRepository userRepository;
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void deleteUnverifiedUsers() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Đang dọn dẹp các tài khoản chưa kích hoạt trước thời điểm: {}", now);
        userRepository.deleteByEnabledFalseAndOtpExpirationBefore(now);
    }
}
