package com.oie.cms.dtos.team;

import com.oie.cms.enums.DepartmentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadTeamResDto {
    private Long id;
    private String name;
    private String leadName;
    private String departmentName;
}
