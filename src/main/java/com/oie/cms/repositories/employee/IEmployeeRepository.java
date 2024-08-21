package com.oie.cms.repositories.employee;

import com.oie.cms.entities.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDepartment_Id(Long id, Pageable pageable);

    default Page<Employee> findByDepartmentId(Long id, Pageable pageable) {
        return findByDepartment_Id(id, pageable);
    }
}
