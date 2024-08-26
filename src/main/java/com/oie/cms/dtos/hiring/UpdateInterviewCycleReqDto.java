package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewCycleStatus;
import lombok.*;

@Getter
@Setter
public class UpdateInterviewCycleReqDto {
    private InterviewCycleStatus status;
}
