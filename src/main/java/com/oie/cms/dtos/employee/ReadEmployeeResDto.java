package com.oie.cms.dtos.employee;

import com.oie.cms.enums.EmployeeRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReadEmployeeResDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String nationalId;
    private EmployeeRole role;
    private LocalDate hiringDate;
    private String departmentName;
}
