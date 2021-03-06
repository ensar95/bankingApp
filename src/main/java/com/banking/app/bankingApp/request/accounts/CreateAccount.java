package com.banking.app.bankingApp.request.accounts;


import java.util.Date;

public class CreateAccount {
    private String accountName;
    private Date expirationDate;

    public CreateAccount() {
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
}
