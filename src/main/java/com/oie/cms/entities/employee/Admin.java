package com.oie.cms.entities.employee;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("admin")
@Getter
@Setter
public class Admin extends Employee {
}
