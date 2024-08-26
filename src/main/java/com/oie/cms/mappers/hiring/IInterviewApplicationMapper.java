package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.entities.hiring.InterviewApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInterviewApplicationMapper {
    InterviewApplication mapToEntity(AddInterviewApplicationReqDto applicationReqDto);
}
