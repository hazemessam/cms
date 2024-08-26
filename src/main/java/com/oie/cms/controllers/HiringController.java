package com.oie.cms.controllers;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationResDto;
import com.oie.cms.dtos.hiring.ReadInterviewApplicationResDto;
import com.oie.cms.services.HiringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/hiring")
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
    public ResponseEntity<AddInterviewApplicationResDto> addInterviewApplication(
            @Valid @RequestBody AddInterviewApplicationReqDto applicationReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hiringService.addInterviewApplication(applicationReqDto));
    }
}
