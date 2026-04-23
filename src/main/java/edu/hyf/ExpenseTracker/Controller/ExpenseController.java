package edu.hyf.ExpenseTracker.Controller;


import edu.hyf.ExpenseTracker.Model.Expense;
import edu.hyf.ExpenseTracker.Repositories.ExpenseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository repo;

    public ExpenseController(ExpenseRepository repo) {
        this.repo = repo;
    }
    //test
    @GetMapping("/test")
    public String test() {
        return "API IS WORKING";
    }

    // CREATE
    @PostMapping
    public Expense create(@RequestBody Expense expense) {
        return repo.save(expense);
    }

    // READ ALL
    @GetMapping
    public List<Expense> getAll() {
        return repo.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense newExpense) {
        return repo.findById(id).map(expense -> {
            expense.setTitle(newExpense.getTitle());
            expense.setAmount(newExpense.getAmount());
            expense.setCategory(newExpense.getCategory());
            expense.setDate(newExpense.getDate());
            return repo.save(expense);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }


    @GetMapping("/category/{category}")
    public List<Expense> getByCategory(@PathVariable String category) {
        return repo.findByCategory(category);
    }

   /* @GetMapping("/amount/{amount}")
    public List<Expense> getByAmount(@PathVariable Double amount) {
        return repo.findByAmountGreaterThan(amount);
    }*/


    @GetMapping("/total")
    public double getTotal() {
        return repo.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @GetMapping("/date")
    public List<Expense> getByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        return repo.findByDateBetween(
                java.time.LocalDate.parse(start),
                java.time.LocalDate.parse(end)
        );
    }
}