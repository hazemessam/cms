package com.oie.cms.repositories.employee;

import com.oie.cms.entities.employee.TeamLead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamLeadRepository extends JpaRepository<TeamLead, Long> {
}
