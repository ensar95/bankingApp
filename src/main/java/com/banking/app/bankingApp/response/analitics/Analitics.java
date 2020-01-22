package com.banking.app.bankingApp.response.analitics;

import java.time.LocalDateTime;

public class Analitics {
    private java.time.LocalDateTime date;
    private Double income;
    private Double expenses;

    public Analitics() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
