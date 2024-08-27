package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UpdateInterviewReqDto {
    private InterviewType type;

    private Instant appointment;

    @Min(value = 1, message = "Rating min value is 1")
    @Max(value = 10, message = "Rating max value is 10")
    private Integer rating;

    private Long interviewerId;
}
