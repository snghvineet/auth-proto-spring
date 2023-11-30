package com.example.authprototype.controller;

import com.example.authprototype.annotation.WithMockAdmin;
import com.example.authprototype.annotation.WithMockUser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HomeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testThat_PublicEndpoint_CanBeAccessedByAnyone() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

    @Test
    public void testThat_SecuredEndpoint_CannotBeAccessedPublicly() throws Exception {
        mockMvc
                .perform(get("/secured"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testThat_SecuredEndpoint_CanBeAccessedByAuthenticatedUsed() throws Exception {
        mockMvc
                .perform(get("/secured"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsStringIgnoringCase("Accessing a secure route")));

    }


    @Test
    public void testThat_AdminEndpoint_CannotBeAccessedPublicly() throws Exception {
        mockMvc
                .perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testThat_AdminEndpoint_CannotBeAccessedByNonAdmin() throws Exception {
        mockMvc
                .perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    public void testThat_AdminEndpoint_CanBeAccessedByAdmin() throws Exception {
        mockMvc
                .perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsStringIgnoringCase("Accessing an admin route")));

    }


}
