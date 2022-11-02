package com.testehan.hibernate.basics.mappings.inheritance.v4.joinedTables;

import javax.persistence.Entity;

@Entity
public class BankAccount extends BillingDetails {

    protected String accountNumber;

    protected String bankName;

    protected String swift;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", swift='" + swift + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
