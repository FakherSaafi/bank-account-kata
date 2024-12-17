package com.kata.bankAccount.controllers;

import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.services.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for managing bank account operations.
 * Provides endpoints for deposit, withdrawal, and fetching the account statement.
 * @author Fakher Saafi
 */
@RestController
@RequestMapping("/api")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Handles POST requests to deposit an amount into the account.
     *
     * @param amount the deposit amount
     * @return a success message with the new balance or an error message
     */
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam double amount) {
        try {
            double newBalance = bankAccountService.deposit(amount);
            return ResponseEntity.ok("Deposit successful. New balance: " + newBalance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Handles POST requests to withdraw an amount from the account.
     *
     * @param amount the withdrawal amount
     * @return a success message with the new balance or an error message
     */
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam double amount) {
        try {
            double newBalance = bankAccountService.withdraw(amount);
            return ResponseEntity.ok("Withdrawal successful. New balance: " + newBalance);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Handles GET requests to retrieve the account statement.
     *
     * @return a list of transactions sorted by date in descending order
     */
    @GetMapping("/statement")
    public ResponseEntity<String> getStatement() {
        List<Transaction> transactions = bankAccountService.getStatement();
        if (transactions.isEmpty()) {
            return ResponseEntity.ok("No transactions found.");
        }

        String statement = transactions.stream()
                .sorted((t1, t2) -> t2.getDate().compareTo(t1.getDate()))
                .map(transaction -> String.format(
                        "Date: %s, Operation: %s, Amount: %.2f, Balance: %.2f",
                        transaction.getDate(), transaction.getTransactionType(), transaction.getAmount(), transaction.getBalance()))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok("Account Statement:\n" + statement);
    }
}
