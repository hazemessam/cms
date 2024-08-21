package com.oie.cms.controllers;

import com.oie.cms.dtos.employee.ReadEmployeeResDto;
import com.oie.cms.dtos.team.AddTeamMemberReqDto;
import com.oie.cms.dtos.team.ReadTeamResDto;
import com.oie.cms.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    TODO:
    - update team by id
    - remove employee from team
 */

@RestController()
@RequestMapping("api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/{teamId}")
    public ResponseEntity<ReadTeamResDto> getTeamById(@PathVariable Long teamId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getTeamById(teamId));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long teamId) {
        teamService.deleteTeamById(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<ReadEmployeeResDto>> getTeamMembers(
            @PathVariable Long teamId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getTeamMembers(teamId));
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<Void> addTeamMember(
            @PathVariable Long teamId,
            @Valid @RequestBody AddTeamMemberReqDto addTeamMemberDto) {
        teamService.addTeamMember(teamId, addTeamMemberDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
