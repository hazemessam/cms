package com.oie.cms.controllers;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.employee.*;
import com.oie.cms.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<ReadEmployeeResDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployeeById(id));
    }

    @GetMapping("/")
    public ResponseEntity<PaginationResDto<ReadEmployeeResDto>> getEmployees(
            @Valid @ModelAttribute ReadEmployeesReqDto filterOptions,
            Pageable paginationOptions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.getEmployees(filterOptions, paginationOptions));
    }

    @PostMapping("/")
    public ResponseEntity<AddEmployeeResDto> addEmployee(@Valid @RequestBody AddEmployeeReqDto addEmpDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.addEmployee(addEmpDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeReqDto updateEmpDto) {
        employeeService.updateEmployee(id, updateEmpDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
