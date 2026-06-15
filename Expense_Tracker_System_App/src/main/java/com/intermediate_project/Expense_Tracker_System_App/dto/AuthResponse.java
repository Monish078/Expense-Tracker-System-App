package com.intermediate_project.Expense_Tracker_System_App.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthResponse {  // after hitting login request  user get the auth response which have

    private String token;
    private String name;
    private String email;
    private String role;
}
