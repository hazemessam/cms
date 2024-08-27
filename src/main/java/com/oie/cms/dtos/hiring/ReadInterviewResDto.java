package com.oie.cms.dtos.hiring;

import com.oie.cms.enums.InterviewType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ReadInterviewResDto {
    private Long id;
    private InterviewType type;
    private Instant appointment;
    private Integer rating;
    private Long interviewerId;
    private String interviewerName;
}
