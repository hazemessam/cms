package com.oie.cms.dtos.hiring;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadInterviewCandidateResDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
