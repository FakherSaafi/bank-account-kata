package com.kata.bankAccount.controllers;

import com.kata.bankAccount.entities.Transaction;
import com.kata.bankAccount.entities.OperationType;
import com.kata.bankAccount.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author Fakher Saafi
 */
@ExtendWith(MockitoExtension.class) // Enable Mockito in JUnit 5
class BankAccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankAccountService bankAccountService; // Mocked service layer

    @InjectMocks
    private BankAccountController bankAccountController; // Controller under test

    @Test
    void deposit_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        double depositAmount = 100.0;
        double newBalance = 200.0;
        when(bankAccountService.deposit(depositAmount)).thenReturn(newBalance);

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(post("/api/deposit")
                        .param("amount", String.valueOf(depositAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit successful. New balance: " + newBalance));

        verify(bankAccountService).deposit(depositAmount);
    }

    @Test
    void deposit_ShouldReturnBadRequestOnInvalidAmount() throws Exception {
        // Arrange
        double depositAmount = -50.0;
        when(bankAccountService.deposit(depositAmount)).thenThrow(new IllegalArgumentException("Deposit amount must be positive."));

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(post("/api/deposit")
                        .param("amount", String.valueOf(depositAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Deposit amount must be positive."));

        verify(bankAccountService).deposit(depositAmount);
    }

    @Test
    void withdraw_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        double withdrawAmount = 50.0;
        double newBalance = 150.0;
        when(bankAccountService.withdraw(withdrawAmount)).thenReturn(newBalance);

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(post("/api/withdraw")
                        .param("amount", String.valueOf(withdrawAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdrawal successful. New balance: " + newBalance));

        verify(bankAccountService).withdraw(withdrawAmount);
    }

    @Test
    void withdraw_ShouldReturnBadRequestOnInvalidAmount() throws Exception {
        // Arrange
        double withdrawAmount = 300.0;
        when(bankAccountService.withdraw(withdrawAmount)).thenThrow(new IllegalStateException("Insufficient funds."));

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(post("/api/withdraw")
                        .param("amount", String.valueOf(withdrawAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Insufficient funds."));

        verify(bankAccountService).withdraw(withdrawAmount);
    }

    @Test
    void getStatement_ShouldReturnAccountStatement() throws Exception {
        // Arrange
        List<Transaction> transactions = List.of(
                new Transaction(java.time.LocalDateTime.now(), 100.0, OperationType.DEPOSIT, 100.0),
                new Transaction(java.time.LocalDateTime.now(), -20.0, OperationType.WITHDRAWAL, 80.0)
        );

        when(bankAccountService.getStatement()).thenReturn(transactions);

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(get("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("DEPOSIT")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("WITHDRAWAL")));

        verify(bankAccountService).getStatement();
    }

    @Test
    void getStatement_ShouldReturnNoTransactionsFound() throws Exception {
        // Arrange
        when(bankAccountService.getStatement()).thenReturn(Collections.emptyList());

        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        // Act & Assert
        mockMvc.perform(get("/api/statement")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("No transactions found."));

        verify(bankAccountService).getStatement();
    }
}
