package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.AddInterviewCycleReqDto;
import com.oie.cms.dtos.hiring.ReadInterviewCycleResDto;
import com.oie.cms.entities.hiring.InterviewCycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInterviewCycleMapper {
    InterviewCycle mapToEntity(AddInterviewCycleReqDto addCycleReqDto);

    @Mapping(target = "applicationId", source = "application.id")
    ReadInterviewCycleResDto mapToDto(InterviewCycle cycle);

    List<ReadInterviewCycleResDto> mapToDto(List<InterviewCycle> cycles);

    default PaginationResDto<ReadInterviewCycleResDto> mapToDto(Page<InterviewCycle> cyclesPage) {
        var paginationRes = new PaginationResDto<ReadInterviewCycleResDto>();
        paginationRes.setItemsTotalCount(cyclesPage.getTotalElements());
        paginationRes.setItems(cyclesPage.map(this::mapToDto).getContent());
        return paginationRes;
    }
}
