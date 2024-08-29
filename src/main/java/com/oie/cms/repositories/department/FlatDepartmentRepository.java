package com.oie.cms.repositories.department;

import com.oie.cms.entities.department.FlatDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatDepartmentRepository extends JpaRepository<FlatDepartment, Long> {
}
