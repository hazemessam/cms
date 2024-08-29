package com.oie.cms.auth;

import com.oie.cms.repositories.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@Primary
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(format("There is no user with email %s", email)));
    }
}
