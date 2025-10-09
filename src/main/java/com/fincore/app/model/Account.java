package com.fincore.app.model;

public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public Account() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
