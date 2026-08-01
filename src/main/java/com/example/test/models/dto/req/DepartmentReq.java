package com.example.test.models.dto.req;

import com.example.test.models.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentReq {
    @NotBlank(message = "Mã bộ phận không được để trống")
    private String code;

    @NotBlank(message = "Tên bộ phận không được để trống")
    private String departmentName;

    @NotBlank(message = "Mô tả bộ phận không được để trống")
    private String description;

}
