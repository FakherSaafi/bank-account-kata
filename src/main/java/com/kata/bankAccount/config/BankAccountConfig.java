package com.kata.bankAccount.config;

import com.kata.bankAccount.entities.BankAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fakher Saafi
 */
@Configuration
public class BankAccountConfig {
    /**
     * Define a singleton BankAccount.
     * A singleton aligns with the simplicity of this task,
     * as managing multiple accounts and persistence are not a requirement.
     */
    @Bean
    public BankAccount bankAccount() {
        return new BankAccount(0.0); // Starts with an initial balance of 0.0
    }
}