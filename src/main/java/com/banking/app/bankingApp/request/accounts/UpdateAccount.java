package com.banking.app.bankingApp.request.accounts;

import java.util.Date;

public class UpdateAccount {
    private String accountName;
    private Date expirationDate;
    private String userId;

    public UpdateAccount() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
