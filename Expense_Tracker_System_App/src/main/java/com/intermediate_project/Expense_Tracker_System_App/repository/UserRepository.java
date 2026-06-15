package com.intermediate_project.Expense_Tracker_System_App.repository;

import com.intermediate_project.Expense_Tracker_System_App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
