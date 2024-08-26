package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.Position;
import com.oie.cms.enums.ReferralType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddInterviewApplicationReqDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "CV url is required")
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL")
    private String cvUrl;

    @NotNull(message = "Position is required")
    private Position position;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Referral type is required")
    private ReferralType referralType;

    private Long referralEmployeeId;
}
