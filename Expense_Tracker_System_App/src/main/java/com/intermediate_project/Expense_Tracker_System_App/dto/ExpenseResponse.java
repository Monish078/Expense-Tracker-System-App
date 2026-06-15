package com.intermediate_project.Expense_Tracker_System_App.dto;

import com.intermediate_project.Expense_Tracker_System_App.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseResponse { // expense object client request bejne response

    private Long id;
    private String title;

    private Double amount;

    private Category category;

    private LocalDate date;

    private String description;
    private LocalDateTime createdAt;


}
