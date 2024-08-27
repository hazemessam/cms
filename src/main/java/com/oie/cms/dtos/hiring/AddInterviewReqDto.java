package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AddInterviewReqDto {
    @NotNull(message = "Interview type is required")
    private InterviewType type;

    @NotNull(message = "Appointment is required")
    private Instant appointment;

    @Min(value = 1, message = "Rating min value is 1")
    @Max(value = 10, message = "Rating max value is 10")
    private Integer rating;

    private Long interviewerId;
}
