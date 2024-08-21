package com.oie.cms.dtos.department;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFlatDepartmentEmployeeReqDto {
    @NotNull
    private Long employeeId;
}
