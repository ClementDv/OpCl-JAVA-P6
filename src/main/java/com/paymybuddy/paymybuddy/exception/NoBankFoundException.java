package com.paymybuddy.paymybuddy.exception;

public class NoBankFoundException extends RuntimeException {

    private String bankName;

    public NoBankFoundException(String bankName) {
        super("No bank found");
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}
