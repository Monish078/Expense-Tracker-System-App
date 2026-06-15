package com.intermediate_project.Expense_Tracker_System_App.service;



import com.intermediate_project.Expense_Tracker_System_App.dto.AuthResponse;
import com.intermediate_project.Expense_Tracker_System_App.dto.LoginRequest;
import com.intermediate_project.Expense_Tracker_System_App.dto.RegistrationRequest;
import com.intermediate_project.Expense_Tracker_System_App.model.Role;
import com.intermediate_project.Expense_Tracker_System_App.model.User;
import com.intermediate_project.Expense_Tracker_System_App.repository.UserRepository;
import com.intermediate_project.Expense_Tracker_System_App.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {  // service authentication ke liye

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // registration ke liye
    public String register(RegistrationRequest dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered: " + dto.getEmail());
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        return "user is registered successfully";
    }


    // login ke liye method
    public AuthResponse login(@Valid LoginRequest dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),
                        dto.getPassword())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }


}
