package com.oie.cms.repositories.department;

import com.oie.cms.entities.department.TeamBasedDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamBasedDepartmentRepository extends JpaRepository<TeamBasedDepartment, Long> {
}
