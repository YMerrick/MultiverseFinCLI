# MultiverseFinCLI FinCore CLI Banking

A command line interface banking app built in Java, designed to provide essential banking operations through a terminal-based interface.

## Project Overview

FinCore CLI banking is a comprehensive command-line banking system that allows users to perform various financial operations directly from their terminal. This project serves as a practical implementation of core Java concepts and demonstrates proper project structure, version control, and development best practices

## Intended Features

### Core Banking Operations
- **Account Management**: Create, view, and manage bank accounts
- **Transaction Processing**: Deposit, withdraw, and transfer funds
- **Balance Inquiry**: Check account balances and transaction history
- **User Authentication**: secure login and session management

### Advanced Features
- **Multi-Account Support**: Handle multiple accounts per user
- **Transaction History**: View detailed transaction logs with filtering
- **Account Statements**: Generate and export account statements
- **Interest Calculations**: Calculate and apply interest on savings accounts
- **Data Persistence**: Save and load account data from files

### CLI Interface
- **Interactive Menus**: User-friendly command-line menus
- **Input validation**: Robust input validation and error handling
- **Help System**: Built-in help and documentation
- **Configuration**: Customizable settings and preferences

## Project Structure

```
MultiverseFinCLI/
├── LICENSE
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── fincore
│   │               └── app
│   │                   ├── application
│   │                   │   ├── accounts
│   │                   │   │   └── AccountService.java
│   │                   │   └── auth
│   │                   │       ├── AuthContext.java
│   │                   │       ├── AuthService.java
│   │                   │       └── SessionManager.java
│   │                   ├── boot
│   │                   │   ├── Application.java
│   │                   │   ├── Bootstrapper.java
│   │                   │   ├── MenuBuilder.java
│   │                   │   ├── Services.java
│   │                   │   └── ServicesFactory.java
│   │                   ├── data
│   │                   │   ├── file
│   │                   │   │   ├── CSVAccountStore.java
│   │                   │   │   └── CSVCredentialStore.java
│   │                   │   ├── inmemory
│   │                   │   │   ├── InMemoryAccountStore.java
│   │                   │   │   ├── InMemoryCredentialStore.java
│   │                   │   │   └── InMemorySessionStore.java
│   │                   │   └── security
│   │                   │       ├── BCryptHasher.java
│   │                   │       └── NoHasher.java
│   │                   ├── domain
│   │                   │   ├── account
│   │                   │   │   ├── Account.java
│   │                   │   │   ├── AccountStore.java
│   │                   │   │   ├── CurrentAccount.java
│   │                   │   │   └── OverdraftAccount.java
│   │                   │   ├── identity
│   │                   │   │   ├── Credentials.java
│   │                   │   │   ├── CredentialStore.java
│   │                   │   │   ├── PasswordHasher.java
│   │                   │   │   ├── Session.java
│   │                   │   │   └── SessionStore.java
│   │                   │   └── shared
│   │                   │       ├── AuthException.java
│   │                   │       ├── DuplicateEntityException.java
│   │                   │       ├── InsufficientFundsException.java
│   │                   │       ├── Money.java
│   │                   │       ├── MoneyBuilder.java
│   │                   │       └── MoneyFormatter.java
│   │                   ├── Main.java
│   │                   ├── menu
│   │                   │   ├── actions
│   │                   │   │   ├── CommandFactory.java
│   │                   │   │   ├── CommandHandler.java
│   │                   │   │   ├── DepositAction.java
│   │                   │   │   ├── displayBalanceAction.java
│   │                   │   │   ├── LoginAction.java
│   │                   │   │   ├── LogoutAction.java
│   │                   │   │   ├── RegisterAction.java
│   │                   │   │   ├── TraversalAction.java
│   │                   │   │   └── WithdrawAction.java
│   │                   │   ├── model
│   │                   │   │   ├── MenuAction.java
│   │                   │   │   ├── MenuComponent.java
│   │                   │   │   ├── MenuDirective.java
│   │                   │   │   ├── MenuGroup.java
│   │                   │   │   ├── MenuItem.java
│   │                   │   │   ├── MenuRenderer.java
│   │                   │   │   ├── MenuResponse.java
│   │                   │   │   └── MenuResponseBuilder.java
│   │                   │   └── nav
│   │                   │       └── MenuNavigator.java
│   │                   └── presentation
│   │                       └── cli
│   │                           ├── io
│   │                           │   ├── CliIO.java
│   │                           │   ├── InputProvider.java
│   │                           │   ├── IOHandler.java
│   │                           │   ├── NumberedIO.java
│   │                           │   ├── OutputRenderer.java
│   │                           │   └── PasswordReader.java
│   │                           ├── loop
│   │                           │   └── CliLoop.java
│   │                           └── port
│   │                               └── CliMenuRenderer.java
│   └── test
│       └── java
│           └── com
│               └── fincore
│                   └── app
│                       ├── application
│                       │   ├── accounts
│                       │   │   └── AccountServiceTest.java
│                       │   └── auth
│                       │       ├── AuthServiceTest.java
│                       │       └── SessionManagerTest.java
│                       ├── cli
│                       │   └── menu
│                       ├── data
│                       │   ├── file
│                       │   ├── inmemory
│                       │   │   ├── InMemoryAccountStoreTest.java
│                       │   │   └── InMemoryCredentialStoreTest.java
│                       │   └── security
│                       │       ├── BCryptHasherTest.java
│                       │       └── NoHasherTest.java
│                       ├── domain
│                       │   ├── account
│                       │   │   ├── AccountTest.java
│                       │   │   ├── CurrentAccountTest.java
│                       │   │   └── OverdraftAccountTest.java
│                       │   └── shared
│                       │       ├── MoneyBuilderTest.java
│                       │       ├── MoneyFormatterTest.java
│                       │       └── MoneyTest.java
│                       └── menu
│                           ├── actions
│                           │   └── CommandsTest.java
│                           ├── model
│                           │   ├── MenuGroupTest.java
│                           │   ├── MenuItemTest.java
│                           │   └── MenuResponseBuilderTest.java
│                           └── nav
│                               └── MenuNavigatorTest.java
└── toDoList.md
```

## Getting Started

1. Ensure you have JDK 21 installed
2. Clone this repository
3. Run ```mvn clean package``` in the terminal of your choosing
4. Run ```java -jar target/*.jar```

## Development Goals

This project focuses on:
- Proper Java project structure and organisation
- Version control best practices with Git
- Clean, maintainable code architecture
- Comprehensive documentation
- Professional development workflow

## Future Enhancements

- CSV integration for persistent storage
- Database integration for persistent storage
- Multi-currency support
- Multi-account support
- **Transaction History**: View detailed transaction logs with filtering
- **Account Statements**: Generate and export account statements
- **Interest Calculations**: Calculate and apply interest on savings accounts

## License
This project is part of a Java Software Engineering curriculum and is intended for educational purposes.
See `LICENSE`