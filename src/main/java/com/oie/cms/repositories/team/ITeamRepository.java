package com.oie.cms.repositories.team;

import com.oie.cms.entities.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, Long> {
}
