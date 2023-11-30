package com.example.authprototype.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthenticationToken extends AbstractAuthenticationToken {
    UserPrincipal principal;

    public UserAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
