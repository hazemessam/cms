package com.oie.cms.dtos.employee;

import com.oie.cms.enums.EmployeeRole;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddEmployeeReqDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "National ID is required")
    @Size(min = 14, max = 14, message = "Invalid national ID format")
    private String nationalId;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotNull(message = "Hiring date is required")
    private LocalDate hiringDate;

    @NotNull(message = "Role is required")
    private EmployeeRole role;
}
