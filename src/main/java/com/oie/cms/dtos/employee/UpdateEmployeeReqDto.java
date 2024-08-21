package com.oie.cms.dtos.employee;

import com.oie.cms.enums.EmployeeRole;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateEmployeeReqDto {
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Size(min = 14, max = 14, message = "Invalid national ID format")
    private String nationalId;

    private LocalDate hiringDate;

    private EmployeeRole role;
}
