package com.intermediate_project.Expense_Tracker_System_App.exception;


public class ExpenseNotFoundException extends RuntimeException{
    // custom exception -jab expense na mile to ye exc throw karo

    public ExpenseNotFoundException(Long id) {  // message hayi likh do ba id dena cons me
        super("Expense not found with id: "+id);
    }
}
