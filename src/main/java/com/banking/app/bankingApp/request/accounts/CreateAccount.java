package com.banking.app.bankingApp.request.accounts;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class CreateAccount {
    private String userId;
    @NotEmpty(message = "Account name is required")
    private String accountName;
    @NotEmpty(message = "Expiration date is required")
    private Date expirationDate;

    public CreateAccount() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
