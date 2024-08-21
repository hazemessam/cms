package com.oie.cms.entities.employee;

import com.oie.cms.entities.department.Department;
import com.oie.cms.entities.BaseEntity;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.utils.converters.EmployeeRoleConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Getter
@Setter
public abstract class Employee extends BaseEntity {
    private String name;

    private String email;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "phone_number")
    private String phoneNumber;

    // TODO: Should be hashed when implementing the authentication
    private String password;

    @Column(name = "hiring_date")
    private LocalDate hiringDate;

    @Column(insertable = false, updatable = false)
    @Convert(converter = EmployeeRoleConverter.class)
    private EmployeeRole role;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}
