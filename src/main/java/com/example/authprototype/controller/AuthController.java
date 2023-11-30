package com.example.authprototype.controller;

import com.example.authprototype.enitity.User;
import com.example.authprototype.model.AuthRequest;
import com.example.authprototype.model.AuthResponse;
import com.example.authprototype.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/login")
    public AuthResponse loginWithEmailAndPassword(@RequestBody AuthRequest request) {
        log.info("Trying to log in...");
        return authService.attemptLoginWithEmailAndPassword(
                request.getEmail(), request.getPassword()
        );
    }

    @PostMapping(path = "/signup")
    public User signupWithEmailAndPassword(@RequestBody AuthRequest request) {
        return authService.createUserWithEmailAndPassword(
                request.getEmail(),
                request.getPassword()
        ).get();
    }
}
