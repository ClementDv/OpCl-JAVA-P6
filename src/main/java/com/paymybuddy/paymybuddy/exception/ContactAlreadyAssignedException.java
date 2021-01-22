package com.paymybuddy.paymybuddy.exception;

public class ContactAlreadyAssignedException extends RuntimeException {
    private Long userId;
    private Long contactId;

    public ContactAlreadyAssignedException(Long userId, Long contactId) {
        super("Contact already assigned");
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
