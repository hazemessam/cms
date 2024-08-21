package com.oie.cms.repositories.employee;

import com.oie.cms.entities.employee.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMangerRepository extends JpaRepository<Manager, Long> {
}
