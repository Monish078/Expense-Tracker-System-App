package com.intermediate_project.Expense_Tracker_System_App.controller;

import com.intermediate_project.Expense_Tracker_System_App.dto.AuthResponse;
import com.intermediate_project.Expense_Tracker_System_App.dto.LoginRequest;
import com.intermediate_project.Expense_Tracker_System_App.dto.RegistrationRequest;
import com.intermediate_project.Expense_Tracker_System_App.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegistrationRequest dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

}
