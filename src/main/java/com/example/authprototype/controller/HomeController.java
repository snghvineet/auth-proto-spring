package com.example.authprototype.controller;

import com.example.authprototype.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String publicRoute() {
        return "Hello, world!";
    }

    @GetMapping(path = "/secured")
    public String securedRoute(Principal principal) {
        return "Accessing a secure route by " + principal.getName();
    }

    @GetMapping(path = "/admin")
    public String adminRoute(@AuthenticationPrincipal UserPrincipal principal) {
        return "Accessing an admin route by " + principal.getUserId().toString();
    }
}
