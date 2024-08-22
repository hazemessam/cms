package com.oie.cms.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AdminUtil {
    private final Environment environment;
    private final PasswordEncoder passwordEncoder;

    public void generateAdminHash() {
        var adminPassword = environment.getProperty("ADMIN_PASSWORD");
        if (adminPassword != null && !adminPassword.isEmpty()) {
            var hashedPassword = passwordEncoder.encode(adminPassword);
            log.info("Add initial admin record in the DB with the following hashed password" +
                    " to be able to login and interact with the system: {}", hashedPassword);
        }
    }
}
