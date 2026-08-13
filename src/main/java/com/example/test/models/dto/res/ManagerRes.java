package com.example.test.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerRes {
    private Long memberId;
    private String fullName;
    private String email;
}
