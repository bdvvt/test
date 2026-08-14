package com.example.test.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRes {
    private Long id;
    private String roleName;
    private Long departmentId;
    private Set<PermissionRes> permissions;
}
