package com.oie.cms.repositories.department;

import com.oie.cms.entities.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {
}
