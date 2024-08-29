package com.oie.cms.repositories.employee;

import com.oie.cms.dtos.employee.EmployeesFilterDto;
import com.oie.cms.entities.employee.Employee;
import com.oie.cms.entities.employee.QEmployee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {
    private final JPAQueryFactory queryFactory;

    public CustomEmployeeRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Employee> findByFilter(EmployeesFilterDto filterOptions, Pageable paginationOptions) {
        var employee = QEmployee.employee;

        var filter = new BooleanBuilder();

        var name = filterOptions.getName();
        if (name != null && !name.isEmpty())
            filter.and(employee.name.containsIgnoreCase(name));

        var deptId = filterOptions.getDeptId();
        if (deptId != null)
            filter.and(employee.department.id.eq(deptId));

        var teamId = filterOptions.getTeamId();
        if (teamId != null)
            filter.and(employee.teamMemberships.any().team.id.eq(teamId));

        var totalCount = queryFactory
                .select(employee.count())
                .from(employee)
                .where(filter)
                .fetchOne();

        var employees = queryFactory
                .selectFrom(employee)
                .where(filter)
                .offset(paginationOptions.getOffset())
                .limit(paginationOptions.getPageSize())
                .fetch();

        return new PageImpl<>(employees, paginationOptions, totalCount);
    }
}
