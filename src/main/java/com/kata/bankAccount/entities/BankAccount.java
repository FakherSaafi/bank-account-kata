package com.kata.bankAccount.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fakher Saafi
 */
public class BankAccount {

    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
