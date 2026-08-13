package com.example.test.models.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateReq {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId;
}
