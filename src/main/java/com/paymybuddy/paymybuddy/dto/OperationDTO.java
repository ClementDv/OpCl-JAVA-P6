package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.model.Operation;

import java.time.LocalDateTime;

public class OperationDTO {

    private String emitter;

    private String receiver;

    private LocalDateTime at;

    private double amount;

    public OperationDTO() {
    }

    public OperationDTO(String emitter, String receiver, LocalDateTime at, double amount) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.at = at;
        this.amount = amount;
    }

    public OperationDTO build(Operation operation) {
        return new OperationDTO().setEmitter(operation.getEmitter())
                .setReceiver(operation.getReceiver())
                .setAt(operation.getAt())
                .setAmount(operation.getAmount());
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
}