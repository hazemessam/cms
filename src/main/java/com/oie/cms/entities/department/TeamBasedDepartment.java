package com.oie.cms.entities.department;

import com.oie.cms.entities.team.Team;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("team_based")
@Getter
@Setter
public class TeamBasedDepartment extends Department {
    @OneToMany(mappedBy = "department")

    private List<Team> teams;
}