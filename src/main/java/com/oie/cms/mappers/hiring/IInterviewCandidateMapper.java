package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.dtos.hiring.ReadInterviewCandidateResDto;
import com.oie.cms.entities.hiring.InterviewCandidate;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInterviewCandidateMapper {
    InterviewCandidate mapToEntity(AddInterviewApplicationReqDto applicationReqDto);

    ReadInterviewCandidateResDto mapToDto(InterviewCandidate candidate);

    List<ReadInterviewCandidateResDto> mapToDto(List<InterviewCandidate> candidates);

    default PaginationResDto<ReadInterviewCandidateResDto> mapToDto(
            Page<InterviewCandidate> candidatesPage) {
        var paginationRes = new PaginationResDto<ReadInterviewCandidateResDto>();
        paginationRes.setItemsTotalCount(candidatesPage.getTotalElements());
        paginationRes.setItems(candidatesPage.map(this::mapToDto).getContent());
        return paginationRes;
    }
}
