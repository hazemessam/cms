package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewCycleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddInterviewCycleReqDto {
    private InterviewCycleStatus status;

    @NotNull(message = "Application ID is required")
    private Long applicationId;
}
