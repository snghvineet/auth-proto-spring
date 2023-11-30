package com.example.authprototype.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtEncryptionAlgorithmConfig {
    private final JwtKeysProvider jwtKeysProvider;
    @Bean
    public Algorithm encryptionAlgorithm() {
        try {
            return Algorithm.RSA256(jwtKeysProvider.getRSAPublicKey(), jwtKeysProvider.getRSAPrivateKey());
        } catch (Exception e) {
            e.printStackTrace();
            return Algorithm.HMAC256("Secret");
        }
    }
}
