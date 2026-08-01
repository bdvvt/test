package com.example.test.models.dto.req;

import com.example.test.validations.annotations.FileExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUpdateReq {
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2 đến 100 ký tự")
    private String fullName;

    @Pattern(regexp = "^(03|05|07|08|09[2689])[0-9]{7}$", message = "Số điện thoại không đúng định dạng Việt Nam")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @FileExtension(allowedExtensions = {".jpg",".png",".webp"},message = "File không đúng định dạng")
    private MultipartFile avatarFile;
}
