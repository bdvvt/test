package com.example.test.models.dto.req;

import com.example.test.validations.annotations.FileExtension;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReq {
    @NotBlank(message = "Tên user không được để trống")
    @Size(min = 2, max = 100, message = "Tên user phải từ 2 đến 100 ký tự")
    private String fullName;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 2, max = 100, message = "Tên đăng nhập phải từ 2 đến 100 ký tự")
    private String username;

    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @Pattern(regexp = "^(03|05|07|08|09[2689])[0-9]{7}$", message = "Số điện thoại không đúng định dạng Việt Nam")
    private String phoneNumber;

    @Past(message = "Ngày sinh không đúng định dạng")
    private LocalDate dateOfBirth;

    @FileExtension(allowedExtensions = {".jpg",".png",".webp"},message = "File không đúng định dạng")
    private MultipartFile avatarFile;

    @NotNull(message = "role không được để trống")
    private Set<Long> roles;

    private Long departmentId;

    private Long organizationId;
}
