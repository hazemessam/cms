package com.oie.cms.controllers;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.department.*;
import com.oie.cms.dtos.employee.ReadEmployeeResDto;
import com.oie.cms.dtos.employee.ReadEmployeesReqDto;
import com.oie.cms.dtos.team.AddTeamReqDto;
import com.oie.cms.dtos.team.AddTeamResDto;
import com.oie.cms.dtos.team.ReadTeamResDto;
import com.oie.cms.services.DepartmentService;
import com.oie.cms.services.EmployeeService;
import com.oie.cms.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final TeamService teamService;

    @GetMapping("/{deptId}")
    public ResponseEntity<ReadDepartmentResDto> getDepartmentById(@PathVariable Long deptId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(departmentService.getDepartmentById(deptId));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReadDepartmentResDto>> getDepartments() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(departmentService.getDepartments());
    }

    @PostMapping("/")
    public ResponseEntity<AddDepartmentResDto> addDepartment(
            @Valid @RequestBody AddDepartmentReqDto paginationOptions) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.addDepartment(paginationOptions));
    }

    @PatchMapping("/{deptId}")
    public ResponseEntity<Void> updateDepartment(
            @PathVariable Long deptId,
            @Valid @RequestBody UpdateDepartmentReqDto updateDeptDto) {
        departmentService.updateDepartment(deptId, updateDeptDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long deptId) {
        departmentService.deleteDepartmentById(deptId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{deptId}/employees")
    public ResponseEntity<PaginationResDto<ReadEmployeeResDto>> getDepartmentEmployees(
            @PathVariable Long deptId,
            Pageable paginationOptions) {
        var deptFilter = ReadEmployeesReqDto.builder().deptId(deptId).build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployees(deptFilter, paginationOptions));
    }

    @PostMapping("/{deptId}/employees")
    public ResponseEntity<Void> addEmployeeToFlatDepartment(
            @PathVariable Long deptId,
            @Valid @RequestBody AddFlatDepartmentEmployeeReqDto addEmpDto
    ) {
        departmentService.addEmployeeToFlatDepartment(deptId, addEmpDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{deptId}/employees/{empId}")
    public ResponseEntity<Void> removeFlatDepartmentEmployee(
            @PathVariable Long deptId,
            @PathVariable Long empId) {
        departmentService.removeEmployeeFromFlatDepartment(deptId, empId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{deptId}/teams")
    public ResponseEntity<List<ReadTeamResDto>> getDepartmentTeams(@PathVariable Long deptId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamService.getTeamsByDeptId(deptId));
    }

    @PostMapping("/{deptId}/teams")
    public ResponseEntity<AddTeamResDto> addTeamToDepartment(
            @PathVariable Long deptId,
            @Valid @RequestBody AddTeamReqDto addTeamDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.addTeamToDepartment(deptId, addTeamDto));
    }
}
