package edu.hyf.ExpenseTracker.Repositories;

import edu.hyf.ExpenseTracker.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

        // Derived query methods
        List<Expense> findByCategory(String category);

    List<Expense> findByDateBetween(LocalDate start, LocalDate end);

}

