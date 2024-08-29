package com.oie.cms.services;

import com.oie.cms.dtos.common.PaginationResDto;
import com.oie.cms.dtos.employee.*;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.repositories.department.DepartmentRepository;
import com.oie.cms.repositories.employee.EmployeeRepository;
import com.oie.cms.mappers.EmployeeMapper;
import com.oie.cms.repositories.team.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class EmployeeService {
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public ReadEmployeeResDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::mapToDto)
                .orElseThrow(() -> new NotFoundBusinessException(format("There is no employee with id %d", id)));
    }

    public PaginationResDto<ReadEmployeeResDto> getEmployees(
            EmployeesFilterDto filterOptions, Pageable paginationOptions) {
        var deptId = filterOptions.getDeptId();
        if (deptId != null && !departmentRepository.existsById(deptId)) {
            throw new NotFoundBusinessException(format("There is no department with id %d", deptId));
        }

        var teamId = filterOptions.getTeamId();
        if (teamId != null && !teamRepository.existsById(teamId)) {
            throw new NotFoundBusinessException(format("There is no team with id %d", teamId));
        }

        return employeeMapper.mapToDto(employeeRepository.findByFilter(filterOptions, paginationOptions));
    }

    public AddEmployeeResDto addEmployee(AddEmployeeReqDto addEmpDto) {
        var emp = employeeMapper.mapToEntity(addEmpDto);
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));

        Long empId = employeeRepository.save(emp).getId();
        return AddEmployeeResDto.builder().id(empId).build();
    }

    public void updateEmployee(Long id, UpdateEmployeeReqDto updateEmpDto) {
        var emp = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(format("There is no employee with id %d", id)));

        if (updateEmpDto.getName() != null) emp.setName(updateEmpDto.getName());
        if (updateEmpDto.getEmail() != null) emp.setEmail(updateEmpDto.getEmail());
        if (updateEmpDto.getPhoneNumber() != null) emp.setPhoneNumber(updateEmpDto.getPhoneNumber());
        if (updateEmpDto.getNationalId() != null) emp.setNationalId(updateEmpDto.getNationalId());
        if (updateEmpDto.getHiringDate() != null) emp.setHiringDate(updateEmpDto.getHiringDate());
        if (updateEmpDto.getPassword() != null) emp.setPassword(passwordEncoder.encode(updateEmpDto.getPassword()));

        employeeRepository.save(emp);
    }

    public void deleteEmployeeById(Long empId) {
        if (!employeeRepository.existsById(empId)) {
            throw new NotFoundBusinessException(format("There is no employee with id %d", empId));
        }
        employeeRepository.deleteById(empId);
    }
}
