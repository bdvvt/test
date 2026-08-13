package com.example.test.controllers;

import com.example.test.models.dto.req.ProfileUpdateReq;
import com.example.test.models.dto.res.UserRes;
import com.example.test.models.services.user.IUserService;
import com.example.test.security.principal.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ProfileController {
    private final IUserService userService;
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(userService.findById(userDetails.getUser().getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @Valid @ModelAttribute ProfileUpdateReq req) {

        UserRes updatedProfile = userService.updateProfile(userDetails.getUser().getId(), req);
        return ResponseEntity.ok(updatedProfile);
    }
}
