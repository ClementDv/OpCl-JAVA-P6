package com.example.paymybuddy.paymybuddy.exception;

public class NoEnoughMoneyOnBalanceException extends RuntimeException {

    private final double balance;

    private final double amount;

    public NoEnoughMoneyOnBalanceException(double balance, double amount) {
        super("You have not enough money on balance to transfer this specific amount");
        this.balance = balance;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }
}
