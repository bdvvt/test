package com.example.test.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentRes {
    private Long id;
    private String code;
    private String departmentName;
    private String description;
    private Boolean isPrimary;
    private OrganizationRes organization;
}
