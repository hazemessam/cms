package com.oie.cms.services;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationResDto;
import com.oie.cms.dtos.hiring.ReadInterviewApplicationResDto;
import com.oie.cms.dtos.hiring.ReadInterviewCandidateResDto;
import com.oie.cms.entities.hiring.InterviewCandidate;
import com.oie.cms.enums.ReferralType;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.mappers.hiring.IInterviewApplicationMapper;
import com.oie.cms.mappers.hiring.IInterviewCandidateMapper;
import com.oie.cms.repositories.department.IDepartmentRepository;
import com.oie.cms.repositories.employee.IEmployeeRepository;
import com.oie.cms.repositories.hiring.IInterviewApplicationRepository;
import com.oie.cms.repositories.hiring.IInterviewCandidateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class HiringService {
    private final IInterviewCandidateMapper interviewCandidateMapper;
    private final IInterviewApplicationMapper interviewApplicationMapper;
    private final IInterviewCandidateRepository interviewCandidateRepository;
    private final IInterviewApplicationRepository interviewApplicationRepository;
    private final IDepartmentRepository departmentRepository;
    private final IEmployeeRepository employeeRepository;

    public PaginationResDto<ReadInterviewApplicationResDto> getInterviewApplications(Pageable paginationOptions) {
        var applications = interviewApplicationRepository.findAll(paginationOptions);
        return interviewApplicationMapper.mapToDto(applications);
    }

    public AddInterviewApplicationResDto addInterviewApplication(AddInterviewApplicationReqDto applicationReqDto) {
        var existCandidate = interviewCandidateRepository.findByPhoneNumber(applicationReqDto.getPhoneNumber());

        InterviewCandidate candidate = null;
        if (existCandidate.isPresent()) {
            candidate = existCandidate.get();
        } else {
            candidate = interviewCandidateMapper.mapToEntity(applicationReqDto);
            interviewCandidateRepository.save(candidate);
        }

        var application = interviewApplicationMapper.mapToEntity(applicationReqDto);
        application.setCandidate(candidate);

        var deptId = applicationReqDto.getDepartmentId();
        var department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no department with id %d", deptId)));
        application.setDepartment(department);

        var referralEmployeeId = applicationReqDto.getReferralEmployeeId();
        if (applicationReqDto.getReferralType().equals(ReferralType.INTERNAL) && referralEmployeeId != null) {
            var referralEmployee = employeeRepository.findById(referralEmployeeId)
                    .orElseThrow(() -> new NotFoundBusinessException(
                            format("There is no employee with id %d", referralEmployeeId)));
            application.setReferralEmployee(referralEmployee);
        }

        var applicationId = interviewApplicationRepository.save(application).getId();
        return AddInterviewApplicationResDto.builder().id(applicationId).build();
    }

    public PaginationResDto<ReadInterviewCandidateResDto> getInterviewCandidates(Pageable paginationOptions) {
        var candidates = interviewCandidateRepository.findAll(paginationOptions);
        return interviewCandidateMapper.mapToDto(candidates);
    }
}
