package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.hiring.AddInterviewReqDto;
import com.oie.cms.entities.hiring.Interview;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInterviewMapper {
    Interview mapToEntity(AddInterviewReqDto interviewReqDto);
}
