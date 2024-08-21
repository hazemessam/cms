package com.oie.cms.entities.department;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("flat")
@Getter
@Setter
public class FlatDepartment extends Department {
}
