package com.oie.cms.dtos.department;

import com.oie.cms.enums.DepartmentType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDepartmentReqDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Type is required")
    private DepartmentType type;

    private Long managerId;
}
