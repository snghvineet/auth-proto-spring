package com.example.authprototype.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtToPrincipal {
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal
                .builder()
                .userId(UUID.fromString(jwt.getSubject()))
                .email(jwt.getClaim("e").toString())
                .authorities(extractGrantedAuthoritiesFromClaim(jwt.getClaim("a")))
                .build();
    }

    private List<SimpleGrantedAuthority> extractGrantedAuthoritiesFromClaim(Claim claim) {
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
