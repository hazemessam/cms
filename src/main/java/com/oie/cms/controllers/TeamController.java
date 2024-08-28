package com.oie.cms.controllers;

import com.oie.cms.auth.annotations.Roles;
import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.employee.ReadEmployeeResDto;
import com.oie.cms.dtos.employee.EmployeesFilterDto;
import com.oie.cms.dtos.team.AddTeamMemberReqDto;
import com.oie.cms.dtos.team.ReadTeamResDto;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.services.EmployeeService;
import com.oie.cms.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    TODO:
    - update team by id
    - remove employee from team
 */

@RestController()
@RequestMapping("api/teams")
@Roles({ EmployeeRole.ADMIN, EmployeeRole.HR })
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final EmployeeService employeeService;

    @GetMapping("{teamId}")
    public ResponseEntity<ReadTeamResDto> getTeamById(@PathVariable Long teamId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getTeamById(teamId));
    }

    @DeleteMapping("{teamId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long teamId) {
        teamService.deleteTeamById(teamId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{teamId}/members")
    public ResponseEntity<PaginationResDto<ReadEmployeeResDto>> getTeamMembers(
            @PathVariable Long teamId,
            Pageable paginationOptions
    ) {
        var filter = EmployeesFilterDto.builder().teamId(teamId).build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployees(filter, paginationOptions));
    }

    @PostMapping("{teamId}/members")
    public ResponseEntity<Void> addTeamMember(
            @PathVariable Long teamId,
            @Valid @RequestBody AddTeamMemberReqDto addTeamMemberDto) {
        teamService.addTeamMember(teamId, addTeamMemberDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
