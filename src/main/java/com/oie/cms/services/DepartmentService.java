package com.oie.cms.services;

import com.oie.cms.dtos.department.*;;
import com.oie.cms.entities.department.Department;
import com.oie.cms.entities.department.FlatDepartment;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.exceptions.ConflictBusinessException;
import com.oie.cms.exceptions.NotFoundBusinessException;
import com.oie.cms.mappers.DepartmentMapper;
import com.oie.cms.repositories.department.DepartmentRepository;
import com.oie.cms.repositories.department.FlatDepartmentRepository;
import com.oie.cms.repositories.employee.EmployeeRepository;
import com.oie.cms.repositories.employee.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final DepartmentRepository departmentRepository;
    private final FlatDepartmentRepository flatDepartmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;

    public ReadDepartmentResDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::mapToDto)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no department with id %d", id)));
    }

    public List<ReadDepartmentResDto> getDepartments() {
        return departmentMapper.mapToDto(departmentRepository.findAll());
    }

    public AddDepartmentResDto addDepartment(AddDepartmentReqDto addDeptDto) {
        var dept = departmentMapper.mapToEntity(addDeptDto);

        if (addDeptDto.getManagerId() != null)
            updateDepartmentManager(dept, addDeptDto.getManagerId());

        var deptId = departmentRepository.save(dept).getId();
        return AddDepartmentResDto.builder().id(deptId).build();
    }

    public void updateDepartment(Long id, UpdateDepartmentReqDto updateDeptDto) {
        var dept = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no department with id %d", id)));

        if (updateDeptDto.getName() != null) dept.setName(updateDeptDto.getName());
        if (updateDeptDto.getManagerId() != null) updateDepartmentManager(dept, updateDeptDto.getManagerId());
    }

    private void updateDepartmentManager(Department dept, Long mangerId) {
        var manager = managerRepository.findById(mangerId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no manager with id %d", mangerId)));

        manager.setDepartment(dept);
        dept.setManager(manager);
    }

    public void deleteDepartmentById(Long deptId) {
        if (!departmentRepository.existsById(deptId)) {
            throw new NotFoundBusinessException(format("There is no department with id %d", deptId));
        }
        departmentRepository.deleteById(deptId);
    }

    public void addEmployeeToFlatDepartment(Long deptId, AddFlatDepartmentEmployeeReqDto addEmpDto) {
        var dept = flatDepartmentRepository.findById(deptId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no flat department with id %d", deptId)));

        var empId = addEmpDto.getEmployeeId();
        var emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new NotFoundBusinessException(format("There is no employee with id %d", empId)));

        if (emp.getDepartment() != null) {
            throw new ConflictBusinessException(
                    format("Employee %s (%d) already exist in department %s (%d)",
                            emp.getName(), empId, dept.getName(), dept.getId()));
        }

        var notAllowedRoles = Stream.of(EmployeeRole.MANAGER, EmployeeRole.TEAM_LEAD);
        if (notAllowedRoles.anyMatch(role -> role.equals(emp.getRole()))) {
            throw new ConflictBusinessException(
                    format("Employee of type %s can not be added to a flat department using this API",
                            emp.getRole()));
        }

        emp.setDepartment(dept);
    }

    public void removeEmployeeFromFlatDepartment(Long deptId, Long empId) {
        var emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new NotFoundBusinessException(
                        format("There is no employee with id %d", empId)));

        var empDept = emp.getDepartment();
        if (empDept == null || !empDept.getId().equals(deptId)) {
            throw new ConflictBusinessException(
                    format("Employee %s (%d) doesn't belong to department (%d)",
                            emp.getName(), empId, deptId));
        }

        if (!(empDept instanceof FlatDepartment)) {
            throw new ConflictBusinessException(
                    format("Department %s (%d) is not a flat department", empDept.getName(), deptId));
        }

        var notAllowedRoles = Stream.of(EmployeeRole.MANAGER, EmployeeRole.TEAM_LEAD);
        if (notAllowedRoles.anyMatch(role -> role.equals(emp.getRole()))) {
            throw new ConflictBusinessException(
                    format("Employee of type %s can not be removed from a flat department using this API",
                            emp.getRole()));
        }

        emp.setDepartment(null);
    }
}
