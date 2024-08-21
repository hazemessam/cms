package com.oie.cms.entities.employee;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("manager")
@Getter
@Setter
public class Manager extends Employee {
}
