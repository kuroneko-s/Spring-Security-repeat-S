package com.choidh.securityinflearn;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "adminUser", roles = "ADMIN")
public @interface WithUser {
}
