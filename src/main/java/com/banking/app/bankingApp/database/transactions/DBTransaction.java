package com.banking.app.bankingApp.database.transactions;

import com.banking.app.bankingApp.database.accounts.DBAccount;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DBTRANSACTION")
public class DBTransaction {
    @Id
    private String id;
    private Double amount;
    private String purpose;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private DBAccount sourceAccount;
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private DBAccount destinationAccount;


    public DBTransaction() {
    }

    public DBAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(DBAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public DBAccount getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(DBAccount destinationAccount) {
        this.destinationAccount = destinationAccount;
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
}
