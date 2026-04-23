package edu.hyf.ExpenseTracker.Service;


import edu.hyf.ExpenseTracker.Model.Expense;
import edu.hyf.ExpenseTracker.Repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
    public class ExpenseService {

        private final ExpenseRepository expenseRepository;

        public ExpenseService(ExpenseRepository expenseRepository) {
            this.expenseRepository = expenseRepository;
        }

        // Create or add a new expense
        public Expense addExpense(Expense expense) {

            if (expense.getDate() == null) {
                expense.setDate(LocalDate.now());
            }

            return expenseRepository.save(expense);
        }


        // Get all expenses
        public List<Expense> getAllExpenses() {
            return expenseRepository.findAll();
        }

        // Get expense by ID
        public Optional<Expense> getExpenseById(Long id) {
            return expenseRepository.findById(id);
        }

        // Update an existing expense
        public Expense updateExpense(Long id, Expense updatedExpense) {
            return expenseRepository.findById(id)
                    .map(expense -> {
                        expense.setTitle(updatedExpense.getTitle());
                        expense.setAmount(updatedExpense.getAmount());
                        expense.setCategory(updatedExpense.getCategory());
                        expense.setDate(updatedExpense.getDate());
                        return expenseRepository.save(expense);
                    })
                    .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
        }

        // Delete expense
        public void deleteExpense(Long id) {
            expenseRepository.deleteById(id);
        }

        // Get expenses by category
        public List<Expense> getExpensesByCategory(String category) {
            return expenseRepository.findByCategory(category);
        }

        // Get expenses within date range
        public List<Expense> getExpensesByDateRange(LocalDate start, LocalDate end) {
            return expenseRepository.findByDateBetween(start, end);
        }

        // Total spending
        public double getTotalExpenses() {
            return expenseRepository.findAll()
                    .stream()
                    //.filter(e -> e.getAmount() != null)
                    .mapToDouble(Expense::getAmount)
                    .sum();
        }

    }

