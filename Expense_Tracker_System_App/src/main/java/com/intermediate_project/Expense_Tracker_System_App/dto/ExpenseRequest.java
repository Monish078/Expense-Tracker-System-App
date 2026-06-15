package com.intermediate_project.Expense_Tracker_System_App.dto;


import com.intermediate_project.Expense_Tracker_System_App.model.Category;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {  // request from client expense ko add karne/update e liye
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Category cannot be null")
    private Category category;

//    @NotNull(message = "Date cannot be null")
//    private LocalDate date;

    private String description;

}
