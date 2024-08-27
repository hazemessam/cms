package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.hiring.AddInterviewReqDto;
import com.oie.cms.dtos.hiring.ReadInterviewResDto;
import com.oie.cms.entities.hiring.Interview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInterviewMapper {
    Interview mapToEntity(AddInterviewReqDto interviewReqDto);

    @Mapping(target = "interviewerId", source = "interviewer.id")
    @Mapping(target = "interviewerName", source = "interviewer.name")
    ReadInterviewResDto mapToDto(Interview interview);

    List<ReadInterviewResDto> mapToDto(List<Interview> interviews);
}
