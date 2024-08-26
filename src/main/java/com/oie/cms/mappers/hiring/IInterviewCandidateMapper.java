package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.entities.hiring.InterviewCandidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInterviewCandidateMapper {
    InterviewCandidate mapToEntity(AddInterviewApplicationReqDto applicationReqDto);
}
