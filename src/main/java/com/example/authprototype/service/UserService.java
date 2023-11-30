package com.example.authprototype.service;

import com.example.authprototype.enitity.User;
import com.example.authprototype.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUserWithEmailAndPassword(String email, String password) {
        User newUser = User
                .builder()
                .id(UUID.randomUUID())
                .email(email)
                .password(password)
                .role("ROLE_USER")
                .build();
        return userRepository.save(newUser);
    }
}
