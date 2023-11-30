package com.example.authprototype.service;

import com.example.authprototype.Utils;
import com.example.authprototype.enitity.User;
import com.example.authprototype.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void testThat_FindByEmail_IsInvoked() {
        userService.findUserByEmail("fake@test.com");
        verify(userRepository).findByEmail(eq("fake@test.com"));
    }

    @Test
    public void testThat_RepositorySaveUser_isInvoked () {
        userService.createUserWithEmailAndPassword("fake@test.com", "password");
        verify(userRepository).save(any(User.class));
    }

}
