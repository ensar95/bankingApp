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
    @JoinColumn(name = "sourceAccountId", nullable = false)
    private DBAccount sourceAccountId;
    @ManyToOne
    @JoinColumn(name = "destinationAccountId", nullable = false)
    private DBAccount destinationAccountId;


    public DBTransaction() {
    }

    public DBAccount getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(DBAccount sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public DBAccount getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(DBAccount destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
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
