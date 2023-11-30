package com.example.authprototype.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.authprototype.Utils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
public class JwtIssuerTests {

    private JwtIssuer jwtIssuer;

    @BeforeEach
    public void setup() {
        jwtIssuer = new JwtIssuer(Utils.getTestEncryptionAlgorithm("secret"));
    }

    @Test
    public void testThat_JwtCanBeIssuedOut() {
        UserPrincipal principal = Utils.getTestUserPrincipal();
        String token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(),
                principal
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

        Assertions.assertThat(token).isNotEmpty();
    }
}
