package com.oie.cms.services;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.*;
import com.oie.cms.entities.hiring.InterviewCandidate;
import com.oie.cms.enums.InterviewCycleStatus;
import com.oie.cms.enums.ReferralType;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.mappers.hiring.IInterviewApplicationMapper;
import com.oie.cms.mappers.hiring.IInterviewCandidateMapper;
import com.oie.cms.mappers.hiring.IInterviewCycleMapper;
import com.oie.cms.repositories.department.IDepartmentRepository;
import com.oie.cms.repositories.employee.IEmployeeRepository;
import com.oie.cms.repositories.hiring.IInterviewApplicationRepository;
import com.oie.cms.repositories.hiring.IInterviewCandidateRepository;
import com.oie.cms.repositories.hiring.IInterviewCycleRepository;
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
    private final IInterviewCycleMapper interviewCycleMapper;
    private final IInterviewCandidateRepository interviewCandidateRepository;
    private final IInterviewApplicationRepository interviewApplicationRepository;
    private final IInterviewCycleRepository interviewCycleRepository;
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

    public AddInterviewCycleResDto addInterviewCycle(AddInterviewCycleReqDto addCycleReqDto) {
        var application = interviewApplicationRepository.findById(addCycleReqDto.getApplicationId())
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no application with id %d", addCycleReqDto.getApplicationId())));

        var cycle = interviewCycleMapper.mapToEntity(addCycleReqDto);
        cycle.setApplication(application);

        if (cycle.getStatus() == null) {
            cycle.setStatus(InterviewCycleStatus.IN_PROGRESS);
        }

        var cycleId = interviewCycleRepository.save(cycle).getId();
        return AddInterviewCycleResDto.builder().id(cycleId).build();
    }
}
