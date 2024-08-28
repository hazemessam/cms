package com.oie.cms.auth;

import com.oie.cms.auth.annotations.Public;
import com.oie.cms.auth.annotations.Roles;
import com.oie.cms.enums.EmployeeRole;
import com.oie.cms.exceptions.UnAuthorizedBusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Stream;

@Aspect
@Component
public class AuthorizationAspect {
    @Before("@annotation(Roles) || @within(Roles)")
    public void authorize(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var method = methodSignature.getMethod();

        if (method.getAnnotation(Public.class) != null) return;

        var user = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var canPerformAction = user instanceof AuthUser authUser
                && Stream.of(getAllowedRoles(method))
                .anyMatch(role -> role.equals(authUser.getRole()));

        if (!canPerformAction) {
            throw new UnAuthorizedBusinessException("You don't have the required role to perform this action");
        }

    }

    private EmployeeRole[] getAllowedRoles(Method method) {
        var methodHasRolesAnnotation = method.getAnnotation(Roles.class) != null;
        var rolesAnnotation = methodHasRolesAnnotation
                ? method.getAnnotation(Roles.class)
                : method.getDeclaringClass().getAnnotation(Roles.class);

        return rolesAnnotation.value();
    }
}
