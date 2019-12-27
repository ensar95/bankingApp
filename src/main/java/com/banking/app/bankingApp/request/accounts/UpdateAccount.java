package com.banking.app.bankingApp.request.accounts;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class UpdateAccount {
    @NotEmpty(message = "Account name is required")
    String accountName;
    @NotEmpty(message = "Expiration date is required")
    Date expirationDate;
    @NotEmpty(message = "User ID is required")
    String userId;

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
