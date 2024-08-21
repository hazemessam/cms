package com.oie.cms.repositories.employee;

import com.oie.cms.dtos.employee.EmployeesFilterDto;
import com.oie.cms.entities.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeCustomRepository {
    Page<Employee> findByFilter(EmployeesFilterDto filterOptions, Pageable paginationOptions);
}
