package com.banking.app.bankingApp.database.transactions;

import com.banking.app.bankingApp.response.accounts.Account;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DBTransaction {
    @Id
    private String id;
    private Double amount;
    private String purpose;
    private LocalDateTime createdAt;
    private List<Account> sourceAccount;
    private List<Account> destinationAccount;

    public DBTransaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Account> getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(List<Account> sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public List<Account> getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(List<Account> destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
}
