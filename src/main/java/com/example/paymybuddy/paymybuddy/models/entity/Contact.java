package com.example.paymybuddy.paymybuddy.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {

    @Column(name = "user_id")
    @Id
    private int userId;

    @Column(name = "fk_contact_id")
    private int contactId;

    public Contact() {
    }

    public Contact(int userId, int contactId) {
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
