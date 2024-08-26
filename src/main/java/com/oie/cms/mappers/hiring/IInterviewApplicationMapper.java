package com.oie.cms.mappers.hiring;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.dtos.hiring.ReadInterviewApplicationResDto;
import com.oie.cms.entities.hiring.InterviewApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInterviewApplicationMapper {
    InterviewApplication mapToEntity(AddInterviewApplicationReqDto applicationReqDto);

    @Mapping(target = "candidateName", source = "candidate.name")
    @Mapping(target = "candidateEmail", source = "candidate.email")
    @Mapping(target = "candidatePhoneNumber", source = "candidate.phoneNumber")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "referralEmployeeName", source = "referralEmployee.name")
    ReadInterviewApplicationResDto mapToDto(InterviewApplication application);

    List<ReadInterviewApplicationResDto> mapToDto(List<InterviewApplication> applications);

    default PaginationResDto<ReadInterviewApplicationResDto> mapToDto(
            Page<InterviewApplication> applicationsPage) {
        var paginationRes = new PaginationResDto<ReadInterviewApplicationResDto>();
        paginationRes.setItemsTotalCount(applicationsPage.getTotalElements());
        paginationRes.setItems(applicationsPage.map(this::mapToDto).getContent());
        return paginationRes;
    }
}
