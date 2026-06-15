package com.intermediate_project.Expense_Tracker_System_App.controller;

import com.intermediate_project.Expense_Tracker_System_App.dto.ExpenseRequest;
import com.intermediate_project.Expense_Tracker_System_App.dto.ExpenseResponse;
import com.intermediate_project.Expense_Tracker_System_App.model.Category;
import com.intermediate_project.Expense_Tracker_System_App.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // ───────────────────────────────
    // CRUD
    // ───────────────────────────────

    // crud apis
    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody ExpenseRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll() {
        return ResponseEntity.ok(expenseService.getAllExpense());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest dto) {
        return ResponseEntity.ok(expenseService.updateExpense(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    // ───────────────────────────────
    // Filtering
    // ───────────────────────────────

    @GetMapping("/filter/category")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(
            @RequestParam Category category) {
        return ResponseEntity.ok(expenseService.getByCategory(category));
    }

    @GetMapping("/filter/date-range")
    public ResponseEntity<List<ExpenseResponse>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(expenseService.getByDateRange(startDate, endDate));
    }

    @GetMapping("/filter/month")
    public ResponseEntity<List<ExpenseResponse>> getByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(expenseService.getByMonth(month, year));
    }


    // ───────────────────────────────
    // Reports
    // ───────────────────────────────

    @GetMapping("/reports/monthly-total")  // ye month ka total amount deta hai
    public ResponseEntity<Double> getMonthlyTotal(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(expenseService.getTotalByMonthAndYear(month, year));
    }

    @GetMapping("/reports/category-wise")  // har category ka total amount
    public ResponseEntity<Map<String, Double>> getCategoryWise() {
        return ResponseEntity.ok(expenseService.getCategoryWiseTotal());
    }

    @GetMapping("/reports/highest-category") // category wise highest expenses dikhata hai
    public ResponseEntity<String> getHighestCategory() {
        return ResponseEntity.ok(expenseService.getHighestSpendingCategory());
    }



}
