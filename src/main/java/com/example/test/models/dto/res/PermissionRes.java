package com.example.test.models.dto.res;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionRes {
    private Long id;
    private String name;
    private Set<Long> departmentIds;
}
