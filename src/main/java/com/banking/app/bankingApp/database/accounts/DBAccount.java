package com.banking.app.bankingApp.database.accounts;

import com.banking.app.bankingApp.database.users.DBUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name="DBACCOUNT")
public class DBAccount {
    @Id
    private String id;
    private Date expirationDate;
    private String accountName;
    private LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private DBUser dbUser;

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
        return dbUser.getFirstName() + " " + dbUser.getLastName();
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
