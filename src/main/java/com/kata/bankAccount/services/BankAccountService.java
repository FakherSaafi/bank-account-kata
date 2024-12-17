package com.kata.bankAccount.services;

import com.kata.bankAccount.entities.Transaction;

import java.util.List;

/**
 * @author Fakher Saafi
 */
public interface BankAccountService {
    double deposit(double amount);
    double withdraw(double amount);
    List<Transaction> getStatement();
    double getBalance();
}
