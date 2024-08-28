package com.oie.cms.auth;

import com.oie.cms.entities.employee.Employee;
import com.oie.cms.enums.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@RequiredArgsConstructor
public class AuthUser implements UserDetails {
    private final Employee emp;

    @Override
    public String getUsername() {
        return emp.getEmail();
    }

    @Override
    public String getPassword() {
        return emp.getPassword();
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(emp.getRole().toString()));
    }

    public Employee getEmployee() {
        return emp;
    }

    public EmployeeRole getRole() { return emp.getRole(); }
}
