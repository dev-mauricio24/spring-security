package com.springsecurity.springsecurity.presentation.controller;

import com.springsecurity.springsecurity.presentation.dto.auth.LoginRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserRequestDTO;
import com.springsecurity.springsecurity.service.interfaces.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {
    private final IAuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello secured";
    }

    @GetMapping("/secured2")
    public String secured2() {
        return "Hello secured 2";
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Registering user {}", request);
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        return auth.isAuthenticated() ? "Login OK" : "Login Fail";
    }
}
