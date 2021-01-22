package com.paymybuddy.paymybuddy.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;


@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "emitter", nullable = false, updatable = false)
    private String emitter;

    @Column(name = "receiver", nullable = false, updatable = false)
    private String receiver;

    @CreationTimestamp
    @Column(name = "at", insertable = false, updatable = false)
    private LocalDateTime at;

    @Column(name = "amount", nullable = false, updatable = false)
    private double amount;

    public Operation() {
    }

    public Operation(String emitter, String receiver, double amount) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Operation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmitter() {
        return emitter;
    }

    public Operation setEmitter(String emitter) {
        this.emitter = emitter;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public Operation setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public Operation setAt(LocalDateTime at) {
        this.at = at;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Operation setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Double.compare(operation.amount, amount) == 0 &&
                id.equals(operation.id) &&
                emitter.equals(operation.emitter) &&
                receiver.equals(operation.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emitter, receiver, at, amount);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", emitter=" + emitter +
                ", receiver=" + receiver +
                ", at=" + at +
                ", amount=" + amount +
                '}';
    }
}
