package com.oie.cms.dtos.department;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDepartmentReqDto {
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    private Long managerId;
}
