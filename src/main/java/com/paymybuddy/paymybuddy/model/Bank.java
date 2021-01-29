package com.paymybuddy.paymybuddy.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "banks")
public class Bank implements MoneyHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emitterBankId")
    private List<Operation> emitterBankListOperation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiverBankId")
    private List<Operation> receiverBankListOperation;

    @Override
    public String getCode() {
        return "BNK_" + name + "_" + address;
    }

    public Bank() {
    }

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public Bank setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bank setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Bank setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name) &&
                Objects.equals(address, bank.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                "code=" + getCode() +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
