package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewCycleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddInterviewCycleReqDto {
    private InterviewCycleStatus status;

    @NotNull(message = "Application ID is required")
    private Long applicationId;
}
