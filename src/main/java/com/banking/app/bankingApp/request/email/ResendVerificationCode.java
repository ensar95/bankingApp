package com.banking.app.bankingApp.request.email;

public class ResendVerificationCode {
    private String email;
    private String password;

    public ResendVerificationCode() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
