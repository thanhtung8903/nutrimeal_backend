package com.nutrimeal.nutrimeal_backend.controller;

import com.nutrimeal.nutrimeal_backend.dto.request.LoginRequest;
import com.nutrimeal.nutrimeal_backend.dto.request.SignupRequest;
import com.nutrimeal.nutrimeal_backend.dto.response.UserInfoResponse;
import com.nutrimeal.nutrimeal_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignupRequest request) {
        authService.handleRegisterUser(request);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserInfoResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.handleAuthenticateUser(loginRequest), HttpStatus.OK);
    }

}
