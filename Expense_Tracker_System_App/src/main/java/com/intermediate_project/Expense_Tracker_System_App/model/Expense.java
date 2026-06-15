package com.intermediate_project.Expense_Tracker_System_App.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {  // ek expense table jo expense object ko staor karega

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")  // amont ke validation
    @Column(nullable = false)
    private Double amount;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)  // string me store hoga
    @Column(nullable = false)
    private Category category;

    @NotNull(message = "Date cannot be null")
    @Column(nullable = false)
    private LocalDate date;

    private String description;  // description aabout the expense

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false) // without user there will no  expense
    private User user;  // every expense should associate to the user




    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // jab object bane tab current time set ho jaye
        this.date = LocalDate.now();  // jab create ho jab ki date ave karlo
    }
}
