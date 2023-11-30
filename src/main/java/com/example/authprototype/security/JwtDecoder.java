package com.example.authprototype.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class  JwtDecoder {
    private final Algorithm encryptionAlgorithm;
    public DecodedJWT decode(String token) {
        return JWT
                .require(encryptionAlgorithm)
                .build()
                .verify(token);
    }
}
