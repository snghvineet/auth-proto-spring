package com.example.authprototype.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithMockUser(roles = {"ROLE_ADMIN"})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithMockAdmin {
}
