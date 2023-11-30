package com.example.authprototype.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final Algorithm encryptionAlgorithm;
    public String issue(UUID userId, String email, List<String> roles) {

        return JWT.create().withSubject(userId.toString())
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.HOURS)))
                .withClaim("e", email)
                .withClaim("r", roles)
                .sign(encryptionAlgorithm);
    }
}
