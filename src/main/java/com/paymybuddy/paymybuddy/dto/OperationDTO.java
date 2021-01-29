package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.model.Operation;

import java.time.LocalDateTime;
import java.util.Objects;

public class OperationDTO {

    private String emitter;

    private String receiver;

    private LocalDateTime at;

    private double amount;

    private String description;

    public OperationDTO() {
    }

    public OperationDTO(String emitter, String receiver, LocalDateTime at, double amount, String description) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.at = at;
        this.amount = amount;
        this.description = description;
    }

    public OperationDTO build(Operation operation) {
        if (operation.getEmitterBankId() != null && operation.getReceiverUserId() != null) {
            return new OperationDTO().setEmitter(operation.getEmitterBankId().getCode())
                    .setReceiver(operation.getReceiverUserId().getCode())
                    .setAt(operation.getAt())
                    .setAmount(operation.getAmount())
                    .setDescription(operation.getDescription());
        } else if (operation.getEmitterUserId() != null && operation.getReceiverBankId() != null) {
            return new OperationDTO().setEmitter(operation.getEmitterUserId().getCode())
                    .setReceiver(operation.getReceiverBankId().getCode())
                    .setAt(operation.getAt())
                    .setAmount(operation.getAmount())
                    .setDescription(operation.getDescription());
        } else if (operation.getEmitterUserId() != null && operation.getReceiverUserId() != null) {
            return new OperationDTO().setEmitter(operation.getEmitterUserId().getCode())
                    .setReceiver(operation.getReceiverUserId().getCode())
                    .setAt(operation.getAt())
                    .setAmount(operation.getAmount())
                    .setDescription(operation.getDescription());
        }
        return null;
    }

    public String getEmitter() {
        return emitter;
    }

    public OperationDTO setEmitter(String emitter) {
        this.emitter = emitter;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public OperationDTO setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public OperationDTO setAt(LocalDateTime at) {
        this.at = at;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public OperationDTO setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OperationDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDTO that = (OperationDTO) o;
        return Double.compare(that.amount, amount) == 0 &&
                emitter.equals(that.emitter) &&
                receiver.equals(that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emitter, receiver, amount);
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "emitter='" + emitter + '\'' +
                ", receiver='" + receiver + '\'' +
                ", at=" + at +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
