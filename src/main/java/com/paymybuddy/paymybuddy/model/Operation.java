package com.paymybuddy.paymybuddy.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitterBankId", updatable = false)
    private Bank emitterBankId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitterUserId", updatable = false)
    private User emitterUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverBankId", updatable = false)
    private Bank receiverBankId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverUserId", updatable = false)
    private User receiverUserId;

    @CreationTimestamp
    @Column(name = "at", insertable = false, updatable = false)
    private LocalDateTime at;

    @Column(name ="description")
    private String description;

    @Column(name = "amount", nullable = false, updatable = false)
    private double amount;

    public Operation() {
    }

    public Operation(Bank emitterBankId, User emitterUserId, Bank receiverBankId, User receiverUserId, String description, double amount) {
        this.emitterBankId = emitterBankId;
        this.emitterUserId = emitterUserId;
        this.receiverBankId = receiverBankId;
        this.receiverUserId = receiverUserId;
        this.description = description;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Operation setId(Long id) {
        this.id = id;
        return this;
    }

    public Bank getEmitterBankId() {
        return emitterBankId;
    }

    public Operation setEmitterBankId(Bank emitterBankId) {
        this.emitterBankId = emitterBankId;
        return this;
    }

    public User getEmitterUserId() {
        return emitterUserId;
    }

    public Operation setEmitterUserId(User emitterUserId) {
        this.emitterUserId = emitterUserId;
        return this;
    }

    public Bank getReceiverBankId() {
        return receiverBankId;
    }

    public Operation setReceiverBankId(Bank receiverBankId) {
        this.receiverBankId = receiverBankId;
        return this;
    }

    public User getReceiverUserId() {
        return receiverUserId;
    }

    public Operation setReceiverUserId(User receiverUserId) {
        this.receiverUserId = receiverUserId;
        return this;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public Operation setAt(LocalDateTime at) {
        this.at = at;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Operation setDescription(String description) {
        this.description = description;
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
                Objects.equals(emitterBankId, operation.emitterBankId) &&
                Objects.equals(emitterUserId, operation.emitterUserId) &&
                Objects.equals(receiverBankId, operation.receiverBankId) &&
                Objects.equals(receiverUserId, operation.receiverUserId) &&
                at.equals(operation.at) &&
                description.equals(operation.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emitterBankId, emitterUserId, receiverBankId, receiverUserId, at, description, amount);
    }
}
