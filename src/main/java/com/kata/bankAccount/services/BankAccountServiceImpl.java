package com.kata.bankAccount.services;

import com.kata.bankAccount.entities.BankAccount;
import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.entities.OperationType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fakher Saafi
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccount bankAccount;

    public BankAccountServiceImpl(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        double newBalance = bankAccount.getBalance() + amount;
        bankAccount.setBalance(newBalance);
        bankAccount.getTransactions().add(new Transaction(LocalDateTime.now(), amount, OperationType.DEPOSIT, newBalance));
        return newBalance;
    }

    public double withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > bankAccount.getBalance()) {
            throw new IllegalStateException("Insufficient funds.");
        }
        double newBalance = bankAccount.getBalance() - amount;
        bankAccount.setBalance(newBalance);
        bankAccount.getTransactions().add(new Transaction(LocalDateTime.now(), -amount, OperationType.WITHDRAWAL, newBalance));
        return newBalance;
    }

    public List<Transaction> getStatement() {
        return bankAccount.getTransactions();
    }

    public double getBalance() {
        return bankAccount.getBalance();
    }
}
