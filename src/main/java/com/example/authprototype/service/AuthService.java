package com.example.authprototype.service;

import com.example.authprototype.enitity.User;
import com.example.authprototype.model.AuthResponse;
import com.example.authprototype.security.JwtIssuer;
import com.example.authprototype.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtIssuer jwtIssuer;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> createUserWithEmailAndPassword(String email, String password) {
        return Optional.of(userService.createUserWithEmailAndPassword(email, passwordEncoder.encode(password)));
    }

    public AuthResponse attemptLoginWithEmailAndPassword(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();
        List<String> roles = principal
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
//        System.out.println(token);
        return new AuthResponse(token);
    }
}
