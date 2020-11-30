package com.paymybuddy.paymybuddy.exception;

public enum ErrorCodesEnum {
    CONTACT_ALREADY_ASSIGNED(400, "CONTACT_ALREADY_ASSIGNED"),
    NOT_ENOUGH_MONEY(400, "NOT_ENOUGH_MONEY"),
    NON_VALID_AMOUNT(400, "NON_VALID_AMOUNT"),
    NO_USER_FOUND(412, "NO_USER_FOUND"),
    NON_VALID_EMAIL(400, "NON_VALID_EMAIL"),
    NO_BANK_FOUND(412, "NO_BANK_FOUND");

    private int code;
    private String error;
    ErrorCodesEnum(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
