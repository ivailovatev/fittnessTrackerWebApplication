package com.ivaylo.exercise_tracker.controller;

import com.ivaylo.exercise_tracker.dto.AuthResponse;
import com.ivaylo.exercise_tracker.dto.LoginRequest;
import com.ivaylo.exercise_tracker.dto.RegisterRequest;
import com.ivaylo.exercise_tracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
