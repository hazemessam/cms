package com.oie.cms.repositories.team;

import com.oie.cms.entities.team.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {
}
