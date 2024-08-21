package com.oie.cms.entities.team;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.entities.department.TeamBasedDepartment;
import com.oie.cms.entities.employee.Employee;
import com.oie.cms.entities.employee.TeamLead;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private TeamBasedDepartment department;

    @OneToOne
    @JoinColumn(name = "lead_id", referencedColumnName = "id")
    private TeamLead lead;

    @ManyToMany
    @JoinTable(
            name = "employees_teams",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )

    private List<Employee> members;
}
