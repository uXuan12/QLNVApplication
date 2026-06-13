package com.DATN.DTO.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PositionRequest {
    @NotNull(message = "Mã phòng ban không được để trống")
    private Integer departmentId;

    @NotNull(message = "Tên chức vụ không được để trống")
    private String title;

    private String description;
}
