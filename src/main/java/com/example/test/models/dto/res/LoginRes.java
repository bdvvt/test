package com.example.test.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRes {
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Set<String> roles;
    private String accessToken;
    private String type;
}
