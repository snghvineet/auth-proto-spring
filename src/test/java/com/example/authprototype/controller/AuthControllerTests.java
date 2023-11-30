package com.example.authprototype.controller;

import com.example.authprototype.service.AuthService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTests {
    @Autowired
    private MockMvc mvc;
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;


}
