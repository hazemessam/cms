package com.oie.cms.dtos.employee;

import com.oie.cms.enums.EmployeeRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EmployeesFilterDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String nationalId;
    private LocalDate hiringDate;
    private EmployeeRole role;
    private Long deptId;
    private Long teamId;
}