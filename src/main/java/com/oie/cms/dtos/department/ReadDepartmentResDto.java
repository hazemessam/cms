package com.oie.cms.dtos.department;

import com.oie.cms.enums.DepartmentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadDepartmentResDto {
    private Long id;
    private String name;
    private DepartmentType type;
    private String managerName;
}
