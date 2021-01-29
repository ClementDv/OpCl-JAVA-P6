package com.paymybuddy.paymybuddy.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements MoneyHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private double balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emitterUserId")
    private List<Operation> emitterUserListOperation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiverUserId")
    private List<Operation> receiverUserListOperation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Contact> userListContact;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contact")
    private List<Contact> contactListContact;

    @Override
    public String getCode() {
        return "USR_" + email;
    }

    public User() {
    }

    public User(String email, String password, double balance) {
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public User setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "code=" + getCode() +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}

