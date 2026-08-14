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
public class PermissionReq {
    @NotBlank(message = "Tên quyền không được để trống")
    private String name;
    private Long departmentId;
}
