package com.example.authprototype.annotation;

import com.example.authprototype.security.UserAuthenticationToken;
import com.example.authprototype.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MockUserSecurityContext implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser withMockUser) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(withMockUser.roles())
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserPrincipal principal = UserPrincipal.builder()
                .userId(UUID.fromString(withMockUser.userId()))
                .email(withMockUser.email())
                .authorities(authorities)
                .build();

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserAuthenticationToken(principal));

        return context;
    }
}
