package com.example.authprototype.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authprototype.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Testing decoding needs a fresh accessToken else this will always throw expired token exception. ")
public class JwtDecoderTests {
    private JwtDecoder jwtDecoder;

    @BeforeEach
    public void setup() {
        jwtDecoder = new JwtDecoder(Utils.getTestEncryptionAlgorithm("secret"));
    }

    @Test
    public void testThat_JwtTokenCanBeDecoded() {
        String testJwtToken = Utils.getTestJwtToken();
        DecodedJWT decodedJWT = jwtDecoder.decode(testJwtToken);
        assertThat(decodedJWT).isNotNull();
        assertThat(decodedJWT.getAlgorithm()).isEqualTo("HS256");
    }
}
