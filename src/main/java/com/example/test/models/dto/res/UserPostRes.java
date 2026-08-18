package com.example.test.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRes {
    private Long id;
    private String fullName;
    private DepartmentRes department;
    private OrganizationRes organization;
}
