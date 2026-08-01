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
public class OrganizationReq {
    @NotBlank(message = "Tên tổ chức không được để trống")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
}
