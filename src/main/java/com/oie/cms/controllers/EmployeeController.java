package com.oie.cms.controllers;

import com.oie.cms.auth.annotations.Roles;
import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.employee.*;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
@Roles({ EmployeeRole.ADMIN, EmployeeRole.HR })
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("{empId}")
    public ResponseEntity<ReadEmployeeResDto> getEmployeeById(@PathVariable Long empId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployeeById(empId));
    }

    @GetMapping("")
    public ResponseEntity<PaginationResDto<ReadEmployeeResDto>> getEmployees(
            @Valid @ModelAttribute EmployeesFilterDto filterOptions,
            Pageable paginationOptions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployees(filterOptions, paginationOptions));
    }

    @PostMapping("")
    public ResponseEntity<AddEmployeeResDto> addEmployee(@Valid @RequestBody AddEmployeeReqDto addEmpDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.addEmployee(addEmpDto));
    }

    @PatchMapping("{empId}")
    public ResponseEntity<Void> updateEmployee(
            @PathVariable Long empId,
            @Valid @RequestBody UpdateEmployeeReqDto updateEmpDto) {
        employeeService.updateEmployee(empId, updateEmpDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{empId}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long empId) {
        employeeService.deleteEmployeeById(empId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
