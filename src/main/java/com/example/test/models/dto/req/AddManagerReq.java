package com.example.test.models.dto.req;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddManagerReq {
    @NotNull(message = "Member ID không được để trống")
    private Long memberId;
}
