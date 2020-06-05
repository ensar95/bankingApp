package com.banking.app.bankingApp.request.email;

public class CheckEmailVerificationCode {
    private String userId;
    private String code;

    public CheckEmailVerificationCode() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
