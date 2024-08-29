package com.oie.cms.repositories.employee;

import com.oie.cms.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, CustomEmployeeRepository {
    Optional<Employee> findByEmail(String email);
}
