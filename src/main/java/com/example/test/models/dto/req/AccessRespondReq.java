package com.example.test.models.dto.req;

import com.example.test.models.constants.AccessStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessRespondReq {
    @NotNull(message = "Trạng thái phê duyệt không được để trống")
    private Boolean approve;
}
