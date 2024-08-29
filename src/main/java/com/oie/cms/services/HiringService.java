package com.oie.cms.services;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.*;
import com.oie.cms.entities.hiring.Interview;
import com.oie.cms.entities.hiring.InterviewCandidate;
import com.oie.cms.entities.hiring.InterviewCycle;
import com.oie.cms.enums.InterviewCycleStatus;
import com.oie.cms.enums.ReferralType;
import com.oie.cms.exceptions.ConflictBusinessException;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.mappers.hiring.InterviewApplicationMapper;
import com.oie.cms.mappers.hiring.InterviewCandidateMapper;
import com.oie.cms.mappers.hiring.InterviewCycleMapper;
import com.oie.cms.mappers.hiring.InterviewMapper;
import com.oie.cms.repositories.department.DepartmentRepository;
import com.oie.cms.repositories.employee.EmployeeRepository;
import com.oie.cms.repositories.hiring.InterviewApplicationRepository;
import com.oie.cms.repositories.hiring.InterviewCandidateRepository;
import com.oie.cms.repositories.hiring.InterviewCycleRepository;
import com.oie.cms.repositories.hiring.InterviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class HiringService {
    private final InterviewCandidateMapper interviewCandidateMapper;
    private final InterviewApplicationMapper interviewApplicationMapper;
    private final InterviewCycleMapper interviewCycleMapper;
    private final InterviewMapper interviewMapper;
    private final InterviewCandidateRepository interviewCandidateRepository;
    private final InterviewApplicationRepository interviewApplicationRepository;
    private final InterviewCycleRepository interviewCycleRepository;
    private final InterviewRepository interviewRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

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

    public PaginationResDto<ReadInterviewCycleResDto> getInterviewCycles(Pageable paginationOptions) {
        var cycles = interviewCycleRepository.findAll(paginationOptions);
        return interviewCycleMapper.mapToDto(cycles);
    }

    public AddInterviewCycleResDto addInterviewCycle(AddInterviewCycleReqDto addCycleReqDto) {
        var application = interviewApplicationRepository.findById(addCycleReqDto.getApplicationId())
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no application with id %d", addCycleReqDto.getApplicationId())));

        if (application.getCycle() != null) {
            throw new ConflictBusinessException(
                    format("Application (%s) already has an interview cycle", application.getId()));
        }

        var cycle = interviewCycleMapper.mapToEntity(addCycleReqDto);
        cycle.setApplication(application);

        if (cycle.getStatus() == null) {
            cycle.setStatus(InterviewCycleStatus.IN_PROGRESS);
        }

        var cycleId = interviewCycleRepository.save(cycle).getId();
        return AddInterviewCycleResDto.builder().id(cycleId).build();
    }

    public void updateInterviewCycle(Long cycleId, UpdateInterviewCycleReqDto updateDto) {
        var cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no interview cycle with id %s", cycleId)));

        if (updateDto.getStatus() != null) cycle.setStatus(updateDto.getStatus());

        interviewCycleRepository.save(cycle);
    }

    public List<ReadInterviewResDto> getCycleInterviews(Long cycleId) {
        var cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no interview cycle with id %s", cycleId)));
        return interviewMapper.mapToDto(cycle.getInterviews());
    }

    public AddInterviewResDto addInterview(Long cycleId, AddInterviewReqDto interviewReqDto) {
        var cycle = interviewCycleRepository.findById(cycleId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no interview cycle with id %s", cycleId)));

        var interview = interviewMapper.mapToEntity(interviewReqDto);
        interview.setCycle(cycle);

        var interviewerId = interviewReqDto.getInterviewerId();
        if (interviewerId != null) {
            var interviewer = employeeRepository.findById(interviewerId)
                    .orElseThrow(() -> new NotFoundBusinessException(
                            format("There is no employee with id %d", interviewerId)));

            interview.setInterviewer(interviewer);
        }

        interviewRepository.save(interview);

        if (interviewReqDto.getRating() != null) {
            updateInterviewCycleRating(cycle);
        }

        return AddInterviewResDto.builder().id(interview.getId()).build();
    }

    public void updateInterview(Long interviewId, UpdateInterviewReqDto updateDto) {
        var interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no interview with id %d", interviewId)));

        var type = updateDto.getType();
        if (type != null) interview.setType(type);

        var appointment = updateDto.getAppointment();
        if (appointment != null) interview.setAppointment(appointment);

        var interviewerId = updateDto.getInterviewerId();
        if (interviewerId != null) {
            var interviewer = employeeRepository.findById(interviewerId)
                    .orElseThrow(() -> new NotFoundBusinessException(
                            format("There is no employee with id %d", interviewerId)));
            interview.setInterviewer(interviewer);
        }

        var rating = updateDto.getRating();
        if (updateDto.getRating() != null) {
            interview.setRating(rating);
            updateInterviewCycleRating(interview.getCycle());
        }
    }

    private void updateInterviewCycleRating(InterviewCycle cycle) {
        Float avgRating = null;

        var interviews = cycle.getInterviews();
        if (interviews != null && !interviews.isEmpty()) {
            var sum = interviews.stream()
                    .map(Interview::getRating)
                    .filter(Objects::nonNull)
                    .reduce(0, Integer::sum);

            avgRating = (float) sum / interviews.size();
        }

        cycle.setRating(avgRating);
    }

    public void deleteInterview(Long interviewId) {
        var interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no interview with id %d", interviewId)));

        interviewRepository.deleteById(interviewId);
        interviewRepository.flush();

        updateInterviewCycleRating(interview.getCycle());
    }
}
