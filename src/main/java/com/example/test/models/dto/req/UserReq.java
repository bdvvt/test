package com.example.test.models.dto.req;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReq {
    @NotBlank(message = "Tên user không được để trống")
    @Size(min = 2, max = 100, message = "Tên user phải từ 2 đến 100 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(03|05|07|08|09[2689])[0-9]{7}$", message = "Số điện thoại không đúng định dạng Việt Nam")
    private String phoneNumber;

    @Past(message = "Ngày sinh không đúng")
    private LocalDate dateOfBirth;

    @NotNull(message = "role không được để trống")
    private Long roleId;
}
