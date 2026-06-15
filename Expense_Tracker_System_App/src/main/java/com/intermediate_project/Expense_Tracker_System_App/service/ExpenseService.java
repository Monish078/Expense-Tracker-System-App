package com.intermediate_project.Expense_Tracker_System_App.service;

import com.intermediate_project.Expense_Tracker_System_App.dto.ExpenseRequest;
import com.intermediate_project.Expense_Tracker_System_App.dto.ExpenseResponse;
import com.intermediate_project.Expense_Tracker_System_App.exception.ExpenseNotFoundException;
import com.intermediate_project.Expense_Tracker_System_App.model.Category;
import com.intermediate_project.Expense_Tracker_System_App.model.Expense;
import com.intermediate_project.Expense_Tracker_System_App.model.User;
import com.intermediate_project.Expense_Tracker_System_App.repository.ExpenseRepository;
import com.intermediate_project.Expense_Tracker_System_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    // logged in user ko nikalo
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext()  // ye username yani email dega
                .getAuthentication().getName();

        return userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
    }


    /// ────────────────────────
    // CRUD operations
    ///  ───────────────────────

    // first post expense
    public ExpenseResponse addExpense(ExpenseRequest dto) {
        // 1.first current logged in user ki expenses dekhna hai isliye user niaklo pehle
        User user = getCurrentUser();

        //2. expense me convert and expense me user ko add karo
        Expense expense = modelMapper.map(dto, Expense.class);
        expense.setUser(user);

        //3. save
        expenseRepository.save(expense);

        return modelMapper.map(expense,ExpenseResponse.class);
    }

    // get expense by id
    public ExpenseResponse getExpenseById(Long id) {
        // 1. current logged user
        User user = getCurrentUser();
        //2. expense is exist or not
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(()-> new ExpenseNotFoundException(id));

        //3. is user ki expenses list me ye expense hai ya nhi, yani is expense ka user yahi hina chiye
        if(!expense.getUser().getId().equals(user.getId())) {
            // jab ye expense is user ka na ho
            throw new RuntimeException("Access denied");
        }
        // and just return
        return modelMapper.map(expense,ExpenseResponse.class);
    }

    // get all expense - of current user ki only
    public List<ExpenseResponse> getAllExpense() {
        // 1. logged in user niaklo
        User user = getCurrentUser();
        //2. find expenses by user_id in the expenses table  fk
        List<Expense> all = expenseRepository.findByUserId(user.getId());
        return all
                .stream()
                .map((element) -> modelMapper.map(element, ExpenseResponse.class))
                .toList();
    }

    // delete Expense
    public void deleteExpense(Long id) {
        // 1. current logged user
        User user = getCurrentUser();
        //2 . expense is exist or not
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        //3. is user ki expenses list me ye expense hai ya nhi, yani is expense ka user yahi hona chiye
        if(!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }


        expenseRepository.delete(expense);
    }

    // update expense

    public ExpenseResponse updateExpense(Long id , ExpenseRequest dto) {
        //1. user logged in
        User user = getCurrentUser();

        // first expense exist karta hai ya nhi
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        //3. is user ki expenses list me ye expense hai ya nhi, yani is expense ka user yahi hona chiye
        if(!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }


        //update fields
        expense.setCategory(dto.getCategory());
        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());

        return modelMapper.map(expenseRepository.save(expense),ExpenseResponse.class);
    }

    /// ────────────────────────
    // Filtering operations
    ///  ───────────────────────

    //1. filtering by category -> expenses by category
    // FOOD,//    TRAVEL,//    SHOPPING,//    BILLS,//    ENTERTAINMENT,//    OTHERS
    public List<ExpenseResponse> getByCategory(Category category) {
        //1. logged in  user
        User user = getCurrentUser();
        List<Expense> byCategory = expenseRepository.findByUserIdAndCategory(user.getId(),category);
        return byCategory.stream()
                .map((element) -> modelMapper.map(element, ExpenseResponse.class))
                .toList();
    }

    //2. filtering by category - expense by date range , like june 1 -> june 30

    public List<ExpenseResponse> getByDateRange(LocalDate startDate,LocalDate endDate) {
        //1. logged in  user
        User user = getCurrentUser();

        List<Expense> byCategory = expenseRepository.findByUserIdAndDateBetween(user.getId(),startDate,endDate);
        return byCategory.stream()
                .map((element) -> modelMapper.map(element, ExpenseResponse.class))
                .toList();
    }

    //3. filtering by category -all expense by month , like june 2025 expenses

    public List<ExpenseResponse> getByMonth(int  month,int year) {
        //1. logged in  user
        User user = getCurrentUser();

        List<Expense> byCategory = expenseRepository.findByUserIdAndMonthAndYear(user.getId(),month,year);
        return byCategory.stream()
                .map((element) -> modelMapper.map(element, ExpenseResponse.class))
                .toList();
    }

    /// ────────────────────────
    // Result operations
    ///  ───────────────────────

    //1. total by month
    public Double getTotalByMonthAndYear(int month, int year) {
        //1. logged in  user
        User user = getCurrentUser();

        Double total =  expenseRepository.getTotalByUserIdAndMonthAndYear(user.getId(), month,year);
        return total != null ? total:0.0;
    }

    //2. sare category wise total amount expense ke liye  method
    public Map<String, Double> getCategoryWiseTotal() {
        //1. logged in  user
        User user = getCurrentUser();

        List<Object[]> results = expenseRepository.getCategoryWiseTotalByUserId(user.getId());
        Map<String, Double> report = new HashMap<>();
        for (Object[] row : results) {
            report.put(row[0].toString(), (Double) row[1]);
        }
        return report;
    }

    // 3. highest spending expense in which category
    public String getHighestSpendingCategory() {
        //1. logged in  user
        User user = getCurrentUser();

        List<Object[]> results = expenseRepository.getHighestSpendingCategoryByUserId(user.getId());
        if (results.isEmpty()) return "No data available";
        Object[] top = results.get(0);
        return top[0].toString() + " - ₹" + top[1];
    }




















}
