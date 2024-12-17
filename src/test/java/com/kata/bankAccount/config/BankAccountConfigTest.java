package com.kata.bankAccount.config;

import com.kata.bankAccount.entities.BankAccount;
import com.kata.bankAccount.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author Fakher Saafi
 */
@SpringBootTest
public class BankAccountConfigTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BankAccount bankAccountTest() {
            return new BankAccount(100.0); // For testing, set a different balance
        }
    }

    @Autowired
    private BankAccount bankAccountTest;

    @Test
    public void testBankAccount() {
        assertNotNull(bankAccountTest);
        assertEquals(100.0, bankAccountTest.getBalance()); // The balance is 100.0 in this test
    }

    @Test
    void getTransactions_ShouldReturnEmptyListInitially() {
        // Act
        List<Transaction> transactions = bankAccountTest.getTransactions();

        // Assert
        assertNotNull(transactions, "Transaction list should not be null");
        assertTrue(transactions.isEmpty(), "Transaction list should be empty initially");
    }
}
