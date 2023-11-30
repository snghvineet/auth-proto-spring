package com.example.authprototype;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.authprototype.enitity.User;
import com.example.authprototype.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

public class Utils {
    public static UserPrincipal getTestUserPrincipal() {
        return UserPrincipal
                .builder()
                .userId(UUID.fromString("882941f1-0fdb-47d3-ad6d-5491f8793a80"))
                .email("fake@test.com")
                .password("password")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }

    public static User getTestUser (String email, String password) {
        return User
                .builder()
                .id(UUID.fromString("882941f1-0fdb-47d3-ad6d-5491f8793a80"))
                .email(email)
                .password(password)
                .role("ROLE_USER")
                .build();
    }

    public static Algorithm getTestEncryptionAlgorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }

    public static String getTestJwtToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI4ODI5NDFmMS0wZmRiLTQ3ZDMtYWQ2ZC01NDkxZjg3OTNhODAiLCJleHAiOjE3MDEyNjU3MDksImUiOiJmYWtlQHRlc3QuY29tIiwiciI6WyJST0xFX1VTRVIiXX0.EjSu8s_fo8z2nM1zdI3omNp38VSFAGCPR1SHE8mp4Ww";
    }
}
