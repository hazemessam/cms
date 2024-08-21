package com.oie.cms.entities.department;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.entities.employee.Employee;
import com.oie.cms.entities.employee.Manager;
import com.oie.cms.enums.DepartmentType;
import com.oie.cms.utils.converters.DepartmentTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "departments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
public abstract class Department extends BaseEntity {
    private String name;

    @Column(insertable = false, updatable = false)
    @Convert(converter = DepartmentTypeConverter.class)
    private DepartmentType type;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
