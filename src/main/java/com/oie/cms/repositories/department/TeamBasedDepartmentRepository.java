package com.oie.cms.repositories.department;

import com.oie.cms.entities.department.TeamBasedDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamBasedDepartmentRepository extends JpaRepository<TeamBasedDepartment, Long> {
}
