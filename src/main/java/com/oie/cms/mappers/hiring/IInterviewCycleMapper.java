package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.hiring.AddInterviewCycleReqDto;
import com.oie.cms.entities.hiring.InterviewCycle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInterviewCycleMapper {
    InterviewCycle mapToEntity(AddInterviewCycleReqDto addCycleReqDto);
}
