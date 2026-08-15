package com.example.test.models.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionReq {
    @NotBlank(message = "Tên quyền không được để trống")
    private String name;

    private Set<Long> departmentIds;

}
