package com.oie.cms.controllers;

import com.oie.cms.dtos.hiring.AddInterviewApplicationReqDto;
import com.oie.cms.dtos.hiring.AddInterviewApplicationResDto;
import com.oie.cms.services.HiringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/hiring")
@RequiredArgsConstructor
public class HiringController {
    private final HiringService hiringService;

    @PostMapping("applications")
    public ResponseEntity<AddInterviewApplicationResDto> addInterviewApplication(
            @Valid @RequestBody AddInterviewApplicationReqDto applicationReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hiringService.addInterviewApplication(applicationReqDto));
    }
}
