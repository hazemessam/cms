package com.oie.cms.auth.annotations;

import com.oie.cms.enums.EmployeeRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Roles {
    EmployeeRole[] value();
}
