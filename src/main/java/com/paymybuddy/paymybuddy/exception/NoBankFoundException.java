package com.paymybuddy.paymybuddy.exception;

public class NoBankFoundException extends RuntimeException {

    private String bankName;

    public NoBankFoundException(String bankName) {
        super("No bank found");
        this.bankName = bankName;
    }

    public NoBankFoundException(String message, String bankName) {
        super(message);
        this.bankName = bankName;
    }

    public NoBankFoundException(String message, Throwable cause, String bankName) {
        super(message, cause);
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}
