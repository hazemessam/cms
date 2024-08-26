package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewCycleStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadInterviewCycleResDto {
    private Long id;
    private Long applicationId;
    private InterviewCycleStatus status;
    private Float rating;
}
