package com.example.test.models.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReq {
    @NotBlank(message = "username must be not empty")
    private String username;

    @NotBlank(message = "password must be not empty")
    private String password;
}
