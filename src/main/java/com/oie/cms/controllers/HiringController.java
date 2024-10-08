package com.oie.cms.controllers;

import com.oie.cms.auth.annotations.Public;
import com.oie.cms.auth.annotations.Roles;
import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.*;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.services.HiringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/hiring")
@Roles({ EmployeeRole.ADMIN, EmployeeRole.HR })
@RequiredArgsConstructor
public class HiringController {
    private final HiringService hiringService;

    @GetMapping("applications")
    public ResponseEntity<PaginationResDto<ReadInterviewApplicationResDto>> getInterviewApplications(
            Pageable paginationOptions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hiringService.getInterviewApplications(paginationOptions));
    }

    @PostMapping("applications")
    @Public
    public ResponseEntity<AddInterviewApplicationResDto> addInterviewApplication(
            @Valid @RequestBody AddInterviewApplicationReqDto applicationReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hiringService.addInterviewApplication(applicationReqDto));
    }

    @GetMapping("candidates")
    public ResponseEntity<PaginationResDto<ReadInterviewCandidateResDto>> getInterviewCandidates(
            Pageable paginationOptions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hiringService.getInterviewCandidates(paginationOptions));
    }

    @GetMapping("cycles")
    public ResponseEntity<PaginationResDto<ReadInterviewCycleResDto>> getInterviewCycles(
            Pageable paginationOptions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hiringService.getInterviewCycles(paginationOptions));
    }

    @PostMapping("cycles")
    public ResponseEntity<AddInterviewCycleResDto> addInterviewCycle(
            @Valid @RequestBody AddInterviewCycleReqDto cycleReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hiringService.addInterviewCycle(cycleReqDto));
    }

    @PatchMapping("cycles/{cycleId}")
    public ResponseEntity<Void> updateInterviewCycle(
            @PathVariable Long cycleId, @Valid @RequestBody UpdateInterviewCycleReqDto updateDto) {
        hiringService.updateInterviewCycle(cycleId, updateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("cycles/{cycleId}/interviews")
    public ResponseEntity<List<ReadInterviewResDto>> getCycleInterviews(
            @PathVariable Long cycleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(hiringService.getCycleInterviews(cycleId));
    }

    @PostMapping("cycles/{cycleId}/interviews")
    public ResponseEntity<AddInterviewResDto> addInterview(
            @PathVariable Long cycleId, @Valid @RequestBody AddInterviewReqDto interviewReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hiringService.addInterview(cycleId, interviewReqDto));
    }

    @PatchMapping("interviews/{interviewId}")
    public ResponseEntity<Void> updateInterview(
            @PathVariable Long interviewId, @Valid @RequestBody UpdateInterviewReqDto updateDto) {
        hiringService.updateInterview(interviewId, updateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("interviews/{interviewId}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long interviewId) {
        hiringService.deleteInterview(interviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
