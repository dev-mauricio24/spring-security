package com.springsecurity.springsecurity.presentation.controller;

import com.springsecurity.springsecurity.presentation.dto.auth.LoginRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserRequestDTO;
import com.springsecurity.springsecurity.service.implementation.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailServiceImpl userDetailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDTO request) {
        log.info("Registering user {}", request);
        return ResponseEntity.ok(userDetailService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request) {
        log.info("Login user {}", request);

        return new ResponseEntity<>(
                userDetailService.login(request),
                HttpStatus.OK
        );
    }
}
