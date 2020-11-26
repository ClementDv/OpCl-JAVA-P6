package com.paymybuddy.paymybuddy.exception;

public class NonValidAmountException extends RuntimeException {

    private double amount;

    public NonValidAmountException(double amount){
        super("The amount you entered is not correct");
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
