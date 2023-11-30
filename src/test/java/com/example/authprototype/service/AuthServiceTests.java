package com.example.authprototype.service;

import com.example.authprototype.Utils;
import com.example.authprototype.model.AuthResponse;
import com.example.authprototype.security.JwtIssuer;
import com.example.authprototype.security.UserAuthenticationToken;
import com.example.authprototype.security.UserPrincipal;
import com.example.authprototype.service.AuthService;
import com.example.authprototype.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtIssuer jwtIssuer;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setupMockSecurityContext() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testThat_CreateUserWithEmailAndPassword_IsCalled() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("password");

        authService.createUserWithEmailAndPassword("fake@test.com", "password");

        Mockito.verify(passwordEncoder).encode("password");
        Mockito.verify(userService).createUserWithEmailAndPassword(eq("fake@test.com"), eq("password"));
    }

    @Test
    public void testThat_attemptLoginWithEmailAndPassword_InvokesNecessaryMethods() throws Exception {
        UserPrincipal principal = Utils.getTestUserPrincipal();
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(principal);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                "fake@test.com", "password"
        );

        when(authenticationManager
                .authenticate(
                        usernamePasswordAuthenticationToken
                )
        ).thenReturn(authenticationToken);

        AuthResponse accessToken = authService.attemptLoginWithEmailAndPassword("fake@test.com", "password");

        verify(authenticationManager).authenticate(eq(usernamePasswordAuthenticationToken));
        verify(securityContext).setAuthentication(authenticationToken);
        verify(jwtIssuer).issue(
                principal.getUserId(), principal.getEmail(),
                principal
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    @Test
    public void testThat_attemptLoginWithEmailAndPassword_ReturnsAccessToken() throws Exception {
        UserPrincipal principal = Utils.getTestUserPrincipal();
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(principal);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                "fake@test.com", "password"
        );

        when(authenticationManager
                .authenticate(
                        usernamePasswordAuthenticationToken
                )
        ).thenReturn(authenticationToken);

        when(jwtIssuer
                .issue(any(UUID.class), any(String.class), ArgumentMatchers.<String>anyList())
        ).thenReturn("test_token");

        AuthResponse accessToken = authService.attemptLoginWithEmailAndPassword("fake@test.com", "password");

        assertThat(accessToken).isNotNull();
        assertThat(accessToken.getAccessToken()).isEqualTo("test_token");
    }
}
