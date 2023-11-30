package com.example.authprototype.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipal jwtToPrincipal;

    public JwtAuthFilter(JwtDecoder jwtDecoder, JwtToPrincipal jwtToPrincipal) {
        this.jwtDecoder = jwtDecoder;
        this.jwtToPrincipal = jwtToPrincipal;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractToken(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipal::convert)
                .map(UserAuthenticationToken::new)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        filterChain.doFilter(request, response);

    }

    private Optional<String> extractToken(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
