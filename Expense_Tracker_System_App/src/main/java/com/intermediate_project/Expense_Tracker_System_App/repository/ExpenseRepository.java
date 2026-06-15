package com.intermediate_project.Expense_Tracker_System_App.repository;

import com.intermediate_project.Expense_Tracker_System_App.model.Category;
import com.intermediate_project.Expense_Tracker_System_App.model.Expense;
import com.intermediate_project.Expense_Tracker_System_App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    List<Expense> findByUserId(Long id);

    // Filter by category
    List<Expense> findByUserIdAndCategory(Long id, Category category);

    // Filter by date range
    List<Expense> findByUserIdAndDateBetween(Long id,LocalDate startDate, LocalDate endDate);

    // Filter by month and year -jisme jpa query ka use karke
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId " +
            "AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    List<Expense> findByUserIdAndMonthAndYear(@Param("userId") Long id, @Param("month") int month, @Param("year") int year);


    //  Total expense by month and year , pure month ki total khurch deta hai
    // Monthly total - user specific
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId " +
            "AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    Double getTotalByUserIdAndMonthAndYear(@Param("userId") Long id,@Param("month") int month,@Param("year") int year);


    // Category wise total - har category ka total
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
            "WHERE e.user.id = :userId GROUP BY e.category")
    List<Object[]> getCategoryWiseTotalByUserId(@Param("userId") Long id);

    // // Highest spending category
    // highest spending category ke liye
    @Query("SELECT e.category, SUM(e.amount) as total FROM Expense e " +
            "WHERE e.user.id = :userId GROUP BY e.category ORDER BY total DESC")
    List<Object[]> getHighestSpendingCategoryByUserId(@Param("userId") Long id);






}
