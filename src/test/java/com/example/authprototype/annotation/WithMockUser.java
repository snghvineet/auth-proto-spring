package com.example.authprototype.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockUserSecurityContext.class)
public @interface WithMockUser {
    String userId() default "882941f1-0fdb-47d3-ad6d-5491f8793a80";
    String email() default "fake@test.com";
    String[] roles() default "ROLE_USER";

}
