package com.kata.bankAccount.entities;

import java.time.LocalDateTime;

/**
 * @author Fakher Saafi
 */
public class Transaction {

    private final LocalDateTime date;
    private final double amount;
    private final OperationType operation;
    private double balance;

    public Transaction(LocalDateTime date, double amount, OperationType transactionType, double balance) {
        this.date = date;
        this.amount = amount;
        this.operation = transactionType;
        this.balance = balance;
    }

    // Getters
    public LocalDateTime getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public OperationType getTransactionType() {
        return operation;
    }

    public double getBalance() { return balance; }
}
