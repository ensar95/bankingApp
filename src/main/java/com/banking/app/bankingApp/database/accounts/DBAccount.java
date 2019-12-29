package com.banking.app.bankingApp.database.accounts;

import com.banking.app.bankingApp.database.users.DBUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "DBAccount")
public class DBAccount {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private DBUser dbUser;
    private String owner;
    private Date expirationDate;
    private String accountName;
    private LocalDateTime createdAt;

    public DBAccount() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DBUser getDbUser() {
        return dbUser;
    }

    public void setDbUser(DBUser dbUser) {
        this.dbUser = dbUser;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
