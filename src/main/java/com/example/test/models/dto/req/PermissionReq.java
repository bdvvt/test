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

    /** Form-data: departmentIds=1,2,3 */
    private String departmentIds;

    public Set<Long> getDepartmentIds() {
        if (departmentIds == null || departmentIds.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(departmentIds.split(","))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }
}
