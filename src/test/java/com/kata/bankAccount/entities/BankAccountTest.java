package com.kata.bankAccount.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
/**
 * @author Fakher Saafi
 */
class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        // Initialize a BankAccount with an initial balance of 100.0 before each test
        bankAccount = new BankAccount(100.0);
    }

    @Test
    void getBalance_ShouldReturnInitialBalance() {
        // Act
        double balance = bankAccount.getBalance();

        // Assert
        assertEquals(100.0, balance, "Initial balance should be 100.0");
    }

    @Test
    void setBalance_ShouldUpdateBalance() {
        // Act
        bankAccount.setBalance(200.0);
        double updatedBalance = bankAccount.getBalance();

        // Assert
        assertEquals(200.0, updatedBalance, "Balance should be updated to 200.0");
    }

    @Test
    void getTransactions_ShouldReturnEmptyListInitially() {
        // Act
        List<Transaction> transactions = bankAccount.getTransactions();

        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        assertTrue(transactions.isEmpty(), "Transaction list should be empty initially");
    }

    @Test
    void getTransactions_ShouldAddTransactionSuccessfully() {
        // Arrange
        Transaction transaction = new Transaction(java.time.LocalDateTime.now(), 50.0, OperationType.DEPOSIT, 150.0);

        // Act
        bankAccount.getTransactions().add(transaction);

        // Assert
        assertEquals(1, bankAccount.getTransactions().size(), "Transaction list should have one transaction");
        assertEquals(transaction, bankAccount.getTransactions().getFirst(), "Transaction should match the added transaction");
    }
}
