package com.oie.cms;

import com.oie.cms.utils.AdminUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(CmsApplication.class, args);

        var adminUtil = ctx.getBean(AdminUtil.class);
        adminUtil.generateAdminHash();
    }
}
