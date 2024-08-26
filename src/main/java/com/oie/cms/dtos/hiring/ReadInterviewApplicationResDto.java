package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.Position;
import com.oie.cms.enums.ReferralType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadInterviewApplicationResDto {
    private String candidateName;
    private String candidateEmail;
    private String candidatePhoneNumber;
    private String cvUrl;
    private Position position;
    private String departmentName;
    private ReferralType referralType;
    private String referralEmployeeName;
}
