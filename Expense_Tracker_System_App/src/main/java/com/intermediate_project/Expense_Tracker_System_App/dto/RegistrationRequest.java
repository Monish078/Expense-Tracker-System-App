package com.intermediate_project.Expense_Tracker_System_App.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest { // regi ke time request dto

    @NotBlank(message = "Name cant be blank ")
    private String name;

    @NotBlank(message = "Email cant be blank")
    @Email(message = "Invalid email formate")
    private String email;

    @NotBlank(message = "Password cant be blank")
    private String password;


}
