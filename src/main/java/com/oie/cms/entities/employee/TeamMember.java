package com.oie.cms.entities.employee;

import com.oie.cms.entities.team.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public abstract class TeamMember extends Employee {
    @ManyToMany(mappedBy = "members")
    private List<Team> teams;
}
