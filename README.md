# Bank account kata
> Think of your personal bank account experience. When in doubt, go for the simplest solution

## Requirements

- Deposit and Withdrawal
- Account statement (date, amount, balance)
- Statement printing

The expected result is a service API, and its underlying implementation, that meets the expressed needs.
Nothing more, especially no UI, no persistence.

## User Stories

### US 1

**In order to** save money

**As a** bank client

**I want to** make a deposit in my account

### US 2

**In order to** retrieve some or all of my savings

**As a** bank client

**I want to** make a withdrawal from my account

### US 3

**In order to** check my operations

**As a** bank client

**I want to** see the history (operation, date, amount, balance) of my operations

Swagger Documentation
---------------------

The Swagger UI provides an interface to explore and test the API. You can access the documentation using the following link:
 [Swagger UI - Bank Account API](http://localhost:8080/swagger-ui/index.html)

Technologies Used
-----------------

*   **Java 21**

*   **Spring Boot 3.x**

*   **Springdoc OpenAPI** (for Swagger Documentation)

*   **JUnit 5** (for testing)

*   **Mockito** (for mocking dependencies)