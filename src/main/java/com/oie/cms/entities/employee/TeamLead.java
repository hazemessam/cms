package com.oie.cms.entities.employee;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("team_lead")
@Getter
@Setter
public class TeamLead extends TeamMember {
}
