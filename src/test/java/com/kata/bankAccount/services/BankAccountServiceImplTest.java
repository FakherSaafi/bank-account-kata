package com.kata.bankAccount.services;

import com.kata.bankAccount.entities.BankAccount;
import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.entities.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * @author Fakher Saafi
 */
@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccount bankAccount;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;


    @Test
    void deposit_shouldIncreaseBalanceAndAddTransaction() {
        // Arrange
        double initialBalance = 100.0;
        double depositAmount = 50.0;
        double expectedBalance = 150.0;

        List<Transaction> transactions = new ArrayList<>();
        when(bankAccount.getBalance()).thenReturn(initialBalance);
        when(bankAccount.getTransactions()).thenReturn(transactions);

        // Act
        double newBalance = bankAccountService.deposit(depositAmount);

        // Assert
        assertEquals(expectedBalance, newBalance);
        verify(bankAccount).setBalance(expectedBalance); // Verify balance update
        assertEquals(1, transactions.size()); // Ensure transaction was added
        assertEquals(OperationType.DEPOSIT, transactions.getFirst().getTransactionType());
    }

    @Test
    void withdraw_shouldDecreaseBalanceAndAddTransaction() {
        // Arrange
        double initialBalance = 100.0;
        double withdrawAmount = 40.0;
        double expectedBalance = 60.0;

        List<Transaction> transactions = new ArrayList<>();
        when(bankAccount.getBalance()).thenReturn(initialBalance);
        when(bankAccount.getTransactions()).thenReturn(transactions);

        // Act
        double newBalance = bankAccountService.withdraw(withdrawAmount);

        // Assert
        assertEquals(expectedBalance, newBalance);
        verify(bankAccount).setBalance(expectedBalance);
        assertEquals(1, transactions.size());
        assertEquals(OperationType.WITHDRAWAL, transactions.getFirst().getTransactionType());
    }

    @Test
    void withdraw_shouldThrowExceptionForInsufficientFunds() {
        // Arrange
        double initialBalance = 30.0;
        double withdrawAmount = 50.0;

        when(bankAccount.getBalance()).thenReturn(initialBalance);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> bankAccountService.withdraw(withdrawAmount));
        assertEquals("Insufficient funds.", exception.getMessage());

        verify(bankAccount, never()).setBalance(anyDouble());
    }

    @Test
    void deposit_shouldThrowExceptionForNegativeAmount() {
        // Arrange
        double depositAmount = -20.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.deposit(depositAmount));
        assertEquals("Deposit amount must be positive.", exception.getMessage());

        verify(bankAccount, never()).setBalance(anyDouble());
    }

    @Test
    void withdraw_shouldThrowExceptionForNegativeAmount() {
        // Arrange
        double depositAmount = -20.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.withdraw(depositAmount));
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());

        verify(bankAccount, never()).setBalance(anyDouble());
    }

    @Test
    void getStatement_shouldReturnTransactions() {
        // Arrange
        List<Transaction> mockTransactions = List.of(
                new Transaction(LocalDateTime.now(), 100.0, OperationType.DEPOSIT, 100.0),
                new Transaction(LocalDateTime.now(), -20.0, OperationType.WITHDRAWAL, 80.0)
        );
        when(bankAccount.getTransactions()).thenReturn(mockTransactions);

        // Act
        List<Transaction> transactions = bankAccountService.getStatement();

        // Assert
        assertEquals(2, transactions.size());
        assertEquals(OperationType.DEPOSIT, transactions.get(0).getTransactionType());
        assertEquals(OperationType.WITHDRAWAL, transactions.get(1).getTransactionType());
    }

    @Test
    void getBalance_shouldReturnCurrentBalance() {
        // Arrange
        double balance = 120.0;
        when(bankAccount.getBalance()).thenReturn(balance);

        // Act
        double result = bankAccountService.getBalance();

        // Assert
        assertEquals(balance, result);
        verify(bankAccount).getBalance();
    }
}
