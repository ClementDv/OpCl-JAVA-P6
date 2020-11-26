package com.paymybuddy.paymybuddy.exception;

public class ContactAlreadyAssignedException extends RuntimeException {
    private Long userId;
    private Long contactId;

    public ContactAlreadyAssignedException(Long userId, Long contactId) {
        this.userId = userId;
        this.contactId = contactId;
    }

    public ContactAlreadyAssignedException(String message, Long userId, Long contactId) {
        super(message);
        this.userId = userId;
        this.contactId = contactId;
    }

    public ContactAlreadyAssignedException(String message, Throwable cause, Long userId, Long contactId) {
        super(message, cause);
        this.userId = userId;
        this.contactId = contactId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getContactId() {
        return contactId;
    }
}
