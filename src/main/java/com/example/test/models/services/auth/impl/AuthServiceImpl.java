package com.example.test.models.services.auth.impl;

import com.example.test.exceptions.AuthException;
import com.example.test.exceptions.NotFoundException;
import com.example.test.models.dto.req.ActiveUserReq;
import com.example.test.models.dto.req.BlockReq;
import com.example.test.models.dto.req.LoginReq;
import com.example.test.models.dto.req.RegisterReq;
import com.example.test.models.dto.res.BlockRes;
import com.example.test.models.dto.res.LoginRes;
import com.example.test.models.entities.Role;
import com.example.test.models.entities.User;
import com.example.test.models.mappers.UserMapper;
import com.example.test.models.repositories.IRoleRepository;
import com.example.test.models.repositories.IUserRepository;
import com.example.test.models.services.auth.IAuthService;
import com.example.test.models.services.mail.IMailService;
import com.example.test.security.jwt.JwtProvider;
import com.example.test.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final IRoleRepository roleRepository;
    private final IMailService mailService;

    @Override
    public void register(RegisterReq req) {
        Set<Role> roles = new HashSet<>();
        String otp = String.valueOf((int) ((Math.random() * 900000) + 100000));
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);
        User user = userMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(roles);
        user.setEnabled(false);
        user.setOtpCode(otp);
        user.setOtpExpiration(expiration);
        userRepository.save(user);
    }

    @Override
    public LoginRes login(LoginReq req) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsernameOrEmail(), req.getPassword())
            );
        } catch (DisabledException e) {
            throw new AuthException("Vui lòng active tài khoản trước khi đăng nhập !");
        } catch (LockedException e) {
            throw new AuthException("Tài khoản của bạn đã bị khóa");
        } catch (AuthenticationException e) {
            throw new AuthException("Mật khẩu hoặc tài khoản không đúng");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userDetails);
        return LoginRes.builder()
                .fullName(userDetails.getUser().getFullName())
                .username(userDetails.getUsername())
                .email(userDetails.getUser().getEmail())
                .phoneNumber(userDetails.getUser().getPhoneNumber())
                .dateOfBirth(userDetails.getUser().getDateOfBirth())
                .roles(userDetails.getUser().getRoles()
                        .stream().map(role -> role.getRoleName())
                        .collect(Collectors.toSet())
                )
                .accessToken(token)
                .type("Bearer")
                .build();


    }


    @Override
    public String activeUser(ActiveUserReq req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng với email: " + req.getEmail()));
        if (user.getEnabled()) {
            return "Tài khoản đã được kích hoạt trước đó.";
        }
        if (user.getOtpCode() == null || !user.getOtpCode().equals(req.getOtp())) {
            throw new RuntimeException("Mã OTP không chính xác");
        }

        if (user.getOtpExpiration() == null || user.getOtpExpiration().isBefore(LocalDateTime.now())) {
            String newOtp = String.valueOf((int) ((Math.random() * 900000) + 100000));
            LocalDateTime newExpiration = LocalDateTime.now().plusMinutes(5);
            user.setOtpCode(newOtp);
            user.setOtpExpiration(newExpiration);
            userRepository.save(user);
            mailService.sendOtpMail(user.getEmail(), newOtp);

            throw new RuntimeException("Mã OTP đã hết hạn! Hệ thống đã tự động gửi mã OTP mới về email của bạn.");
        }
        user.setEnabled(true);
        user.setOtpCode(null);
        user.setOtpExpiration(null);
        userRepository.save(user);
        return "Kích hoạt tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.";
    }


}
