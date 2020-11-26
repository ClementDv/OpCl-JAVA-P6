package com.paymybuddy.paymybuddy.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private User contact;

    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public Contact setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Contact setUser(User user) {
        this.user = user;
        return this;
    }

    public User getContact() {
        return contact;
    }

    public Contact setContact(User contact) {
        this.contact = contact;
        return this;
    }
}
