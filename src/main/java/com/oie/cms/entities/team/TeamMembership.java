package com.oie.cms.entities.team;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.entities.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees_teams")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMembership extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee member;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
}
