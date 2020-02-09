package com.banking.app.bankingApp.database.accounts;

import com.banking.app.bankingApp.database.transactions.DBTransaction;
import com.banking.app.bankingApp.database.users.DBUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "DBACCOUNT")
public class DBAccount {
    @Id
    private String id;
    private Date expirationDate;
    private String accountName;
    private LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    private DBUser dbUser;
    @OneToMany(mappedBy = "sourceAccountId")
    private List<DBTransaction> incomes;
    @OneToMany(mappedBy = "destinationAccountId")
    private List<DBTransaction> expenses;

    public DBAccount() {
    }

    public List<DBTransaction> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<DBTransaction> incomes) {
        this.incomes = incomes;
    }

    public List<DBTransaction> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<DBTransaction> expenses) {
        this.expenses = expenses;
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
