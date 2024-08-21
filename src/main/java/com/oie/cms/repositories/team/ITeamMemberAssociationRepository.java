package com.oie.cms.repositories.team;

import com.oie.cms.entities.team.TeamMemberAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITeamMemberAssociationRepository extends JpaRepository<TeamMemberAssociation, Long> {
}
